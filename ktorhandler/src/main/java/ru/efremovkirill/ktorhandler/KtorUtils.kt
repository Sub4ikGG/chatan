package ru.efremovkirill.ktorhandler

import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.headers
import io.ktor.http.ContentType
import io.ktor.http.HeadersBuilder
import io.ktor.http.URLBuilder
import io.ktor.http.URLProtocol
import io.ktor.http.contentType
import io.ktor.http.path
import io.ktor.util.StringValues
import ru.efremovkirill.localstorage.LocalStorage

object KtorUtils {

    private fun HeadersBuilder.appendHeaders() {
        val localStorage = LocalStorage.newInstance()
        append("deviceId", localStorage.get("deviceId") ?: "")
        append("Token", localStorage.get(LocalStorage.TOKEN) ?: "")
        append("Device-Type", "android")
        append("App-Type", "driver")
    }

    private fun URLBuilder.appendUrl(protocol: URLProtocol = URLProtocol.HTTPS, port: Int = -1, host: String, path: String) {
        this.protocol = protocol
        this.host = host

        if (port != -1)
            this.port = port

        path(path)
    }

    fun HttpRequestBuilder.appendRequest(_host: String, _protocol: URLProtocol, port: Int = -1, path: String, stringValues: StringValues) {
        url {
            appendUrl(path = path, host = _host, port = port, protocol = _protocol)
            parameters.appendAll(stringValues)
        }

        headers {
            appendHeaders()
        }

        contentType(ContentType.Application.Json)
    }

}