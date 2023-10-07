package ru.chatan.app.domain.usecases

import org.kodein.di.instance
import ru.chatan.app.di.di
import ru.chatan.app.domain.repository.MessageRepository

class StopMessagesUseCase {
    private val repository: MessageRepository by di.instance()

    operator fun invoke() = repository.stopMessages()

}