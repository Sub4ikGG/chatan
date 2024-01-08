package ru.chatan.app.presentation.createchat

import ru.chatan.app.StateViewModel
import ru.chatan.app.domain.models.chat.CreateChat
import ru.chatan.app.domain.usecases.CreateChatUseCase

class CreateChatViewModel(
    private val createChatUseCase: CreateChatUseCase
) : StateViewModel<CreateChatState, CreateChatEvent>(CreateChatState.initial()) {

    override fun send(event: CreateChatEvent) = run {
        when (event) {
            is CreateChatEvent.CreateChat -> {
                createChat(createChat = event.chat)
            }
        }
    }

    private suspend fun createChat(createChat: CreateChat) {
        mutableState.emit(getStateValue().loading())

        val response = createChatUseCase(createChat = createChat)
        if (response.isSuccess()) {
            val chat = response.data ?: return
            mutableState.emit(getStateValue().success(chat = chat))
        } else mutableState.emit(getStateValue().error(error = response.message))
    }

}