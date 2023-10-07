package ru.chatan.app.domain.repository

import kotlinx.coroutines.flow.StateFlow
import ru.chatan.app.domain.models.message.ChatMessage
import ru.chatan.app.presentation.chat.ChatManager

interface MessageRepository {

    suspend fun getMessages(chatId: Long): StateFlow<List<ChatMessage>>
    suspend fun sendMessage(body: String)
    suspend fun setListener(onChatManagerListener: ChatManager.OnChatManagerListener)
    fun stopMessages()

}