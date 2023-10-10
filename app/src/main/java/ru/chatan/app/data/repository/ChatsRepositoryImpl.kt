package ru.chatan.app.data.repository

import ru.chatan.app.data.models.chat.ChatDTO
import ru.chatan.app.data.models.chat.ConnectChatDTO
import ru.chatan.app.domain.repository.ChatsRepository
import ru.efremovkirill.ktorhandler.KtorClient
import ru.efremovkirill.ktorhandler.Response

class ChatsRepositoryImpl : ChatsRepository {
    override suspend fun getChats(): Response<List<ChatDTO>?> =
        KtorClient.getSafely(path = GET_CHATS_PATH)

    override suspend fun connectChat(code: String): Response<ChatDTO?> =
        KtorClient.postSafely(
            path = CONNECT_CHAT_PATH,
            body = ConnectChatDTO(code = code)
        )

    private companion object {
        private const val GET_CHATS_PATH = "/chats"
        private const val CONNECT_CHAT_PATH = "/connect-to-the-chat"
    }
}