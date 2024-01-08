package ru.chatan.app.domain.usecases

import org.kodein.di.instance
import ru.chatan.app.data.models.chat.ChatDTO
import ru.chatan.app.di.di
import ru.chatan.app.domain.models.chat.Chat
import ru.chatan.app.domain.models.chat.CreateChat
import ru.chatan.app.domain.repository.ChatsRepository
import ru.efremovkirill.ktorhandler.Response

class CreateChatUseCase {
    private val repository: ChatsRepository by di.instance()

    suspend operator fun invoke(createChat: CreateChat): Response<Chat?> =
        repository.createChat(createChat = createChat).map()

    private fun Response<ChatDTO?>.map(): Response<Chat?> =
        Response(
            code = this.code,
            responseMessage = this.message,
            data = this.data?.toModel(),
            errors = this.errors
        )
}