package ru.chatan.app.domain.usecases

import org.kodein.di.instance
import ru.chatan.app.di.di
import ru.chatan.app.domain.repository.MessageRepository

class GetMessagesUseCase {
    private val repository: MessageRepository by di.instance()

    suspend operator fun invoke(chatId: Long) = repository.getMessages(chatId = chatId)

}