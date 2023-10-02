package ru.chatan.app.domain.usecases

import org.kodein.di.instance
import ru.chatan.app.data.models.chat.ChatDTO
import ru.chatan.app.di.di
import ru.chatan.app.domain.models.chat.Chat
import ru.chatan.app.domain.repository.ChatsRepository
import ru.efremovkirill.ktorhandler.Response

/**
 * Получает все чаты пользователя
 */
class GetChatsUseCase {
    private val repository: ChatsRepository by di.instance()

    suspend operator fun invoke() = repository.getChats().map()

    private fun Response<List<ChatDTO>?>.map(): Response<List<Chat>> =
        Response(
            code = this.code,
            responseMessage = this.message,
            data = this.data?.map { it.toModel() }.orEmpty(),
            errors = this.errors
        )
}