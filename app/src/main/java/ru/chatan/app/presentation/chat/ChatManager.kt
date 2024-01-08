package ru.chatan.app.presentation.chat

import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import io.ktor.client.plugins.websocket.DefaultClientWebSocketSession
import io.ktor.client.plugins.websocket.sendSerialized
import io.ktor.client.plugins.websocket.webSocket
import io.ktor.websocket.Frame
import io.ktor.websocket.FrameType
import io.ktor.websocket.close
import io.ktor.websocket.readReason
import io.ktor.websocket.readText
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import ru.chatan.app.UNDEFINED_ERROR
import ru.chatan.app.data.models.chat.AccessDataDTO
import ru.chatan.app.data.models.message.ChatMessageDTO
import ru.chatan.app.data.models.message.SendMessageDTO
import ru.chatan.app.domain.models.message.ChatMessage
import ru.chatan.app.presentation.User
import ru.efremovkirill.ktorhandler.KtorClient
import ru.efremovkirill.localstorage.LocalStorage

class ChatManager {

    private val localStorage = LocalStorage.newInstance()
    private var scope = CoroutineScope(Dispatchers.IO)
    private var session: DefaultClientWebSocketSession? = null

    private var onChatManagerListener: OnChatManagerListener? = null

    private val mutableMessagesFlow: MutableStateFlow<List<ChatMessage>> =
        MutableStateFlow(emptyList())
    private val messagesFlow = mutableMessagesFlow.asStateFlow()

    fun getMessagesFlow() = messagesFlow

    fun initialize(
        chatId: Long
    ) {
        try {
            scope = CoroutineScope(Dispatchers.IO)
            scope.launch {
                try {
                    KtorClient.getWebSocketClient()
                        .webSocket(urlString = WS_URL) {
                            Log.d(TAG, "initialize")
                            session = this
                            onChatManagerListener?.onInitialized()

                            receiveMessages(chatId = chatId)
                            sendAccessData(chatId = chatId).join()
                            while (scope.isActive) delay(10L)
                        }
                }
                catch (e: Exception) {
                    onChatManagerListener?.onError(error = e.localizedMessage ?: UNDEFINED_ERROR)
                    Log.e(TAG, "scope init error: ${e.localizedMessage}")

                    if (scope.isActive) {
                        delay(2000L)
                        initialize(chatId = chatId)
                    }
                    else onDispose()
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "init error: ${e.localizedMessage}")
            onChatManagerListener?.onError(error = e.localizedMessage ?: UNDEFINED_ERROR)

            if (scope.isActive)
                initialize(chatId = chatId)
            else onDispose()
        }
    }

    private fun sendAccessData(chatId: Long) = scope.launch {
        Log.d(TAG, "sendAccessData")
        val deviceId = localStorage.get("deviceId").orEmpty()
        val token = localStorage.get(LocalStorage.TOKEN).orEmpty()

        session?.sendSerialized(
            AccessDataDTO(
                deviceId = deviceId,
                token = token,
                chatId = chatId.toString()
            )
        )
    }

    fun sendMessage(
        body: String
    ) = scope.launch {
        Log.d(TAG, "sendMessage: $body")
        session?.sendSerialized(
            SendMessageDTO(body = body)
        )

        val messages = messagesFlow.value.toMutableList()
        val user = User.user ?: return@launch

        messages.add(
            0,
            ChatMessage(
                id = System.currentTimeMillis(),
                user = user,
                body = body,
                date = System.currentTimeMillis() / 1000
            )
        )

        mutableMessagesFlow.emit(messages)
    }

    private fun receiveMessages(chatId: Long) = scope.launch {
        Log.d(TAG, "receiveMessages")
        while (session != null) {
            try {
                val incoming = session?.incoming?.receive()
                when (incoming?.frameType) {
                    FrameType.TEXT -> {
                        val frame = incoming as Frame.Text?
                        val data = frame?.readText()
                        val listType = object : TypeToken<List<ChatMessageDTO>>() {}.type
                        val message: List<ChatMessageDTO>? = Gson().fromJson(data, listType)

                        Log.d(TAG, "receive: $data")
                        message?.also { mutableMessagesFlow.emit(message.map { it.toModel() }) }
                    }
                    FrameType.BINARY -> {
//                        TODO()
                    }
                    FrameType.CLOSE -> {
                        val frame = incoming as Frame.Close?
                        val reason = frame?.readReason()

                        when (reason?.code?.toInt()) {
                            401 -> {
                                if (KtorClient.updateTokens())
                                    initialize(chatId = chatId)
                                else throw Exception("tokens update failed")
                            }

                            else -> {
                                throw Exception("unresolved code ${reason?.code}")
                            }
                        }
                    }
                    FrameType.PING -> {
//                        TODO()
                    }
                    FrameType.PONG -> {
//                        TODO()
                    }
                    null -> {
//                        TODO()
                    }
                }
            } catch (e: Exception) {
                if (!scope.isActive) return@launch

                Log.e(TAG, "receiveMessages: ${e.localizedMessage}")
                onDispose()
            }
        }
    }

    fun setListener(onChatManagerListener: OnChatManagerListener) {
        this.onChatManagerListener = onChatManagerListener
    }

    fun onDispose() = scope.launch {
        Log.d(TAG, "onDispose")

        if (session?.isActive == true) {
            session?.close()
        }

        scope.cancel()
        session = null
        onChatManagerListener = null
        mutableMessagesFlow.emit(emptyList())
    }

    interface OnChatManagerListener {
        fun onInitialized()
        fun onError(error: String)
    }

    private companion object {
        const val TAG = "ChatManager"
        const val WS_URL = "wss://api.chatan.ru/chat/chat-messages"
    }

}