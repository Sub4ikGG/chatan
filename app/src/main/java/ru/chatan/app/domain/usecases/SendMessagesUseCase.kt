package ru.chatan.app.domain.usecases

import org.kodein.di.instance
import ru.chatan.app.di.di
import ru.chatan.app.domain.repository.MessageRepository

class SendMessagesUseCase {
    private val repository: MessageRepository by di.instance()

    suspend operator fun invoke(body: String) = repository.sendMessage(body = body)

}