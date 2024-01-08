package ru.chatan.app.data.repository

import ru.chatan.app.data.models.chat.ChatDTO
import ru.chatan.app.data.models.chat.ConnectChatDTO
import ru.chatan.app.domain.models.chat.CreateChat
import ru.chatan.app.domain.repository.ChatsRepository
import ru.efremovkirill.ktorhandler.KtorClient
import ru.efremovkirill.ktorhandler.Response

class ChatsRepositoryImpl : ChatsRepository {
    override suspend fun getChats(): Response<List<ChatDTO>?> =
        KtorClient.getSafely(path = GET_CHATS_PATH)

    override suspend fun createChat(createChat: CreateChat): Response<ChatDTO?> =
        KtorClient.postSafely(
            path = CREATE_CHAT_PATH,
            body = createChat.toDTO()
        )

    override suspend fun connectChat(code: String): Response<ChatDTO?> =
        KtorClient.postSafely(
            path = CONNECT_CHAT_PATH,
            body = ConnectChatDTO(code = code)
        )

    private companion object {
        private const val GET_CHATS_PATH = "/chats"
        private const val CREATE_CHAT_PATH = "/create-chat"
        private const val CONNECT_CHAT_PATH = "/connect-to-the-chat"
    }
}