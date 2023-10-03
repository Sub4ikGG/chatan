package ru.chatan.app.presentation.chats

import ru.chatan.app.StateViewModel
import ru.chatan.app.domain.usecases.GetChatsUseCase

class ChatsViewModel(
    private val getChatsUseCase: GetChatsUseCase
): StateViewModel<ChatsState, ChatsEvent>(ChatsState.initial()) {

    init {
        send(ChatsEvent.GetChats)
    }

    override fun send(event: ChatsEvent) = run {
        when (event) {
            ChatsEvent.GetChats -> getChats()
        }
    }

    private suspend fun getChats() {
        mutableState.emit(state.value.loading())

        val response = getChatsUseCase()
        if (response.isSuccess())
            mutableState.emit(state.value.success(chats = response.data.orEmpty()))
        else mutableState.emit(state.value.error(error = response.message))
    }

}