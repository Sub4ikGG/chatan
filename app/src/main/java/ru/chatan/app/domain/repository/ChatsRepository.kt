package ru.chatan.app.domain.repository

import ru.chatan.app.data.models.chat.ChatDTO
import ru.chatan.app.domain.models.chat.CreateChat
import ru.efremovkirill.ktorhandler.Response

interface ChatsRepository {

    suspend fun getChats(): Response<List<ChatDTO>?>
    suspend fun createChat(createChat: CreateChat): Response<ChatDTO?>
    suspend fun connectChat(code: String): Response<ChatDTO?>

}