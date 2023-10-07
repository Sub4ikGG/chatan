package ru.chatan.app.data.repository

import kotlinx.coroutines.flow.StateFlow
import org.kodein.di.instance
import ru.chatan.app.di.di
import ru.chatan.app.domain.models.message.ChatMessage
import ru.chatan.app.domain.repository.MessageRepository
import ru.chatan.app.presentation.chat.ChatManager

class MessageRepositoryImpl : MessageRepository {

    private val chatManager: ChatManager by di.instance()

    override suspend fun getMessages(chatId: Long): StateFlow<List<ChatMessage>> {
        chatManager.initialize(chatId = chatId)
        return chatManager.getMessagesFlow()
    }

    override suspend fun sendMessage(body: String) {
        chatManager.sendMessage(body = body)
    }

    override suspend fun setListener(onChatManagerListener: ChatManager.OnChatManagerListener) {
        chatManager.setListener(onChatManagerListener = onChatManagerListener)
    }

    override fun stopMessages() {
        chatManager.onDispose()
    }

}