package ru.chatan.app.domain.repository

import ru.chatan.app.data.models.chat.ChatDTO
import ru.efremovkirill.ktorhandler.Response

interface ChatsRepository {

    suspend fun getChats(): Response<List<ChatDTO>?>

}