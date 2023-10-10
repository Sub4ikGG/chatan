package ru.chatan.app.presentation.connectchat

import ru.chatan.app.StateViewModel
import ru.chatan.app.domain.usecases.ConnectChatUseCase

class ConnectChatViewModel(
    val connectChatUseCase: ConnectChatUseCase
) : StateViewModel<ConnectChatState, ConnectChatEvent>(ConnectChatState.initial()) {

    override fun send(event: ConnectChatEvent) = run {
        when (event) {
            is ConnectChatEvent.Connect -> connectChat(code = event.code)
        }
    }

    private suspend fun connectChat(code: String) {
        mutableState.emit(getStateValue().loading())

        val response = connectChatUseCase(code = code)
        if (response.isSuccess())
            response.data?.also {
                mutableState.emit(getStateValue().success(chat = it))
            }
        else mutableState.emit(getStateValue().error(error = response.message))
    }

}