package ru.chatan.app.presentation.chat

import android.util.Log
import io.ktor.client.plugins.websocket.DefaultClientWebSocketSession
import io.ktor.client.plugins.websocket.receiveDeserialized
import io.ktor.client.plugins.websocket.sendSerialized
import io.ktor.client.plugins.websocket.webSocket
import io.ktor.websocket.close
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import ru.chatan.app.UNDEFINED_ERROR
import ru.chatan.app.data.models.chat.AccessDataDTO
import ru.chatan.app.data.models.message.ChatMessageDTO
import ru.chatan.app.data.models.message.SendMessageDTO
import ru.chatan.app.domain.models.chat.ChatUser
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
        Log.d(TAG, "initialize")
        try {
            scope = CoroutineScope(Dispatchers.IO)
            scope.launch {
                KtorClient.getWebSocketClient()
                    .webSocket(urlString = WS_URL) {
                        session = this
                        onChatManagerListener?.onInitialized()

                        sendAccessData(chatId = chatId).join()
                        receiveMessages().join()
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
        messages.add(
            0,
            ChatMessage(
                id = System.currentTimeMillis(),
                user = ChatUser(id = 0, name = User.name),
                body = body,
                date = System.currentTimeMillis() / 1000
            )
        )

        mutableMessagesFlow.emit(messages)
    }

    private fun receiveMessages() = scope.launch {
        Log.d(TAG, "receiveMessages")
        while (session != null) {
            try {
                val data = session?.receiveDeserialized<List<ChatMessageDTO>>()
                Log.d(TAG, "receive: $data")
                data?.also { mutableMessagesFlow.emit(data.map { it.toModel() }) }
            } catch (e: Exception) {
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