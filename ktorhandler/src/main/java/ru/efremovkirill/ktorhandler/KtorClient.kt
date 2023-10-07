package ru.efremovkirill.ktorhandler

import android.security.keystore.UserNotAuthenticatedException
import android.util.Log
import android.view.KeyCharacterMap.UnavailableException
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.websocket.WebSockets
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.URLProtocol
import io.ktor.serialization.kotlinx.KotlinxSerializationConverter
import io.ktor.serialization.kotlinx.KotlinxWebsocketSerializationConverter
import io.ktor.serialization.kotlinx.json.json
import io.ktor.util.StringValues
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.sync.Mutex
import kotlinx.serialization.json.Json
import ru.efremovkirill.ktorhandler.KtorUtils.appendRequest
import ru.efremovkirill.ktorhandler.tokens.RefreshTokenDTO
import ru.efremovkirill.localstorage.LocalStorage

object KtorClient {

    const val TAG = "Ktor-Client"
    private const val REQUEST_TIMEOUT = 20_000L
    private lateinit var client: HttpClient

    private val tokenRefreshMutex = Mutex()
    private val serviceUnavailableMap = mutableMapOf<String, Int>()
    private const val serviceUnavailableMaxAttempt = 3

    private val refreshTokenHandler = RefreshTokenHandler()

    private var serviceStatusListener: ServiceStatusListener? = null

    var HOST = "host"
    var PROTOCOL = URLProtocol.HTTPS
    var TOKEN_REFRESH_HOST = "host"
    var TOKEN_REFRESH_PATH = "path"

    fun initialize(
        host: String,
        tokenRefreshHost: String,
        tokenRefreshPath: String,
        protocol: URLProtocol = URLProtocol.HTTPS,
        serviceStatusListener: ServiceStatusListener? = null
    ) {
        Log.i(TAG, "initialized.")

        HOST = host
        PROTOCOL = protocol
        TOKEN_REFRESH_HOST = tokenRefreshHost
        TOKEN_REFRESH_PATH = tokenRefreshPath

        this.serviceStatusListener = serviceStatusListener

        client = HttpClient(CIO) {
            install(HttpTimeout) {
                requestTimeoutMillis = REQUEST_TIMEOUT
            }

            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                    coerceInputValues = false
                    prettyPrint = true
                    isLenient = true
                })
            }

            install(Logging) {
                level = LogLevel.ALL
            }

            engine {
                https {
                    trustManager = TrustAllCertsManager()
                }
            }
        }
    }

    fun getWebSocketClient(): HttpClient {
        val webSocketClient = HttpClient(CIO) {
            install(WebSockets) {
                contentConverter = KotlinxWebsocketSerializationConverter(
                    Json {
                        prettyPrint = true
                        isLenient = true
                        ignoreUnknownKeys = true
                    }
                )
            }

            install(ContentNegotiation) {
                register(
                    ContentType.Application.Json,
                    KotlinxSerializationConverter(
                        Json {
                            prettyPrint = true
                            isLenient = true
                            ignoreUnknownKeys = true
                        }
                    ))
            }
        }

        return webSocketClient
    }

    private fun getClient(): HttpClient = client

    suspend fun rawGet(
        host: String = HOST,
        protocol: URLProtocol = PROTOCOL,
        port: Int = -1,
        path: String,
        body: Any = "",
        query: StringValues = StringValues.Empty,
        logs: Boolean
    ): HttpResponse? {
        if (logs)
            Log.w(
                TAG,
                "\n" +
                        "------------------->\n" +
                        "GET: ${protocol.name}://$host$path\n" +
                        "QUERY: ${query.entries()}\n" +
                        "BODY: $body"
            )
        return try {
            val response = getClient().get {
                appendRequest(
                    path = path,
                    port = port,
                    stringValues = query,
                    _host = host,
                    _protocol = protocol
                )
                setBody(if (body is RefreshTokenDTO) getRefreshTokenDTOFromStorage() else body)
            }

            if (logs)
                Log.w(
                    TAG,
                    "<------------------- ${protocol.name}://$host$path ${response.status}\n" +
                            "BODY: ${response.bodyAsText()}\n" +
                            "HEADER: ${response.headers}"
                )

            if (response.status == HttpStatusCode.Unauthorized)
                throw UserNotAuthenticatedException()

            if (response.status == HttpStatusCode.ServiceUnavailable) {
                if (!serviceUnavailableMap.containsKey(path)) serviceUnavailableMap[path] = 0

                var attempt = serviceUnavailableMap[path] ?: 0
                if (attempt < serviceUnavailableMaxAttempt) {
                    attempt++
                    delay(2500L)
                    serviceUnavailableMap[path] = attempt
                    return rawGet(
                        host = host,
                        protocol = protocol,
                        port = port,
                        path = path,
                        body = body,
                        query = query,
                        logs = logs
                    )
                } else throw UnavailableException("")
            } else serviceUnavailableMap[path] = 0

            serviceStatusListener?.onServiceStatusChanged(status = true)

            return response
        } catch (e: Exception) {
            if (e is UnavailableException)
                serviceStatusListener?.onServiceStatusChanged(status = false)

            Log.e(
                TAG,
                "\n" +
                        "<-------------------\n${protocol.name}://$host$path\n${e.javaClass}\n" +
                        e.localizedMessage
            )

            validateException(
                exception = e,
                unit = {
                    runBlocking {
                        rawGet(
                            host = host,
                            protocol = protocol,
                            port = port,
                            path = path,
                            body = body,
                            query = query,
                            logs = logs
                        )
                    }
                }
            )
        }
    }

    suspend inline fun <reified T> getSafely(
        host: String = HOST,
        protocol: URLProtocol = PROTOCOL,
        port: Int = -1,
        path: String,
        body: Any = "",
        stringValues: StringValues = StringValues.Empty,
        logs: Boolean = true
    ): Response<T?> {
        return try {
            rawGet(
                host = host,
                protocol = protocol,
                path = path,
                port = port,
                body = body,
                query = stringValues,
                logs = logs
            )?.body() ?: Response.empty()
        } catch (e: Exception) {
            Log.e(
                TAG,
                "\n" +
                        "<-------------------\n${protocol.name}://$host$path\n${e.javaClass}\n" +
                        e.printStackTrace()
            )
            return Response.empty()
        }
    }

    suspend fun rawPost(
        host: String = HOST,
        protocol: URLProtocol = PROTOCOL,
        port: Int = -1,
        path: String,
        body: Any = "",
        query: StringValues = StringValues.Empty,
        logs: Boolean
    ): HttpResponse? {
        if (logs)
            Log.w(
                TAG,
                "\n" +
                        "------------------->\n" +
                        "POST: ${protocol.name}://$host$path\n" +
                        "QUERY: ${query.entries()}\n" +
                        "BODY: $body"
            )
        return try {
            val response = getClient().post {
                appendRequest(
                    path = path,
                    port = port,
                    stringValues = query,
                    _host = host,
                    _protocol = protocol
                )
                setBody(if (body is RefreshTokenDTO) getRefreshTokenDTOFromStorage() else body)
            }

            if (logs)
                Log.w(
                    TAG,
                    "<------------------- ${protocol.name}://$host$path ${response.status}\n" +
                            "BODY: ${response.bodyAsText()}\n" +
                            "HEADER: ${response.headers}"
                )

            if (response.status == HttpStatusCode.Unauthorized)
                throw UserNotAuthenticatedException()

            if (response.status == HttpStatusCode.ServiceUnavailable) {
                if (!serviceUnavailableMap.containsKey(path)) serviceUnavailableMap[path] = 0

                var attempt = serviceUnavailableMap[path] ?: 0
                if (attempt < serviceUnavailableMaxAttempt) {
                    attempt++
                    delay(2000L)
                    serviceUnavailableMap[path] = attempt
                    return rawPost(
                        host = host,
                        protocol = protocol,
                        port = port,
                        path = path,
                        body = body,
                        query = query,
                        logs = logs
                    )
                } else throw UnavailableException("")
            } else serviceUnavailableMap[path] = 0

            serviceStatusListener?.onServiceStatusChanged(status = true)

            return response
        } catch (e: Exception) {
            if (e is UnavailableException)
                serviceStatusListener?.onServiceStatusChanged(status = false)

            Log.e(
                TAG,
                "\n" +
                        "<-------------------\n${protocol.name}://$host$path\n${e.javaClass}\n" +
                        e.localizedMessage
            )

            validateException(
                exception = e,
                unit = {
                    runBlocking {
                        rawPost(
                            host = host,
                            protocol = protocol,
                            port = port,
                            path = path,
                            body = body,
                            query = query,
                            logs = logs
                        )
                    }
                }
            )
        }
    }

    suspend inline fun <reified T> postSafely(
        host: String = HOST,
        protocol: URLProtocol = PROTOCOL,
        port: Int = -1,
        path: String,
        body: Any = "",
        stringValues: StringValues = StringValues.Empty,
        logs: Boolean = true
    ): Response<T?> {
        return try {
            rawPost(
                host = host,
                protocol = protocol,
                path = path,
                port = port,
                body = body,
                query = stringValues,
                logs = logs
            )?.body() ?: Response.empty()
        } catch (e: Exception) {
            Log.e(
                TAG,
                "\n" +
                        "<-------------------\n${protocol.name}://$host$path\n${e.javaClass}\n" +
                        e.printStackTrace()
            )
            return Response.empty()
        }
    }

    private fun getRefreshTokenDTOFromStorage(): RefreshTokenDTO {
        val localStorage = LocalStorage.newInstance()
        return RefreshTokenDTO(refreshToken = localStorage.get(LocalStorage.REFRESH_TOKEN) ?: "")
    }

    fun closeClient() {
        client.close()
    }

    private suspend fun validateException(
        exception: Exception,
        unit: () -> HttpResponse?
    ): HttpResponse? {
        if (exception is UserNotAuthenticatedException) {
            if (updateTokens())
                return unit()

            return null
        } else
            return null
    }

    private suspend fun updateTokens(): Boolean {

        // Закрываем mutex
        val lockResult = tokenRefreshMutex.tryLock()
        return if (lockResult) { // Если удалось закрыть - обновляем токены
            try {
                refreshTokenHandler.refreshTokens()
            } finally {

                // Открываем mutex
                tokenRefreshMutex.unlock()
            }
        } else {

            // Ждем когда mutex откроется
            while (tokenRefreshMutex.isLocked) delay(100L)

            // Возвращаем true, значит обновление токенов завершилось
            true
        }
    }

    interface ServiceStatusListener {
        fun onServiceStatusChanged(status: Boolean)
    }

}