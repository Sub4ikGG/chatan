package ru.chatan.app.presentation.chat

import ru.chatan.app.StateViewModel
import ru.chatan.app.domain.models.message.SendMessage
import ru.chatan.app.domain.usecases.GetMessagesUseCase
import ru.chatan.app.domain.usecases.SendMessagesUseCase
import ru.chatan.app.domain.usecases.StopMessagesUseCase

class ChatViewModel(
    private val getMessagesUseCase: GetMessagesUseCase,
    private val stopMessagesUseCase: StopMessagesUseCase,
    private val sendMessagesUseCase: SendMessagesUseCase
): StateViewModel<ChatState, ChatEvent>(ChatState.initial()) {

    override fun send(event: ChatEvent) = run {
        when (event) {
            is ChatEvent.GetMessages -> getMessages(event.chatId)
            is ChatEvent.SendMessage -> sendMessage(event.message)
        }
    }

    private suspend fun getMessages(chatId: Long) {
        getMessagesUseCase(chatId = chatId).collect {
            mutableState.emit(state.value.success(messages = it))
        }
    }

    private suspend fun sendMessage(message: SendMessage) {
        sendMessagesUseCase(body = message.body)
    }

    override fun onCleared() {
        stopMessagesUseCase()

        super.onCleared()
    }
}