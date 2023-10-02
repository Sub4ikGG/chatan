package ru.chatan.app.data.repository

import ru.chatan.app.data.models.chat.ChatDTO
import ru.chatan.app.domain.repository.ChatsRepository
import ru.efremovkirill.ktorhandler.KtorClient
import ru.efremovkirill.ktorhandler.Response

class ChatsRepositoryImpl : ChatsRepository {
    override suspend fun getChats(): Response<List<ChatDTO>?> =
        KtorClient.getSafely(path = GET_CHATS_PATH)

    private companion object {
        private const val GET_CHATS_PATH = "/chats"
    }
}