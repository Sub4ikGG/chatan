package ru.chatan.app.presentation.chat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.chatan.app.domain.usecases.GetMessagesUseCase
import ru.chatan.app.domain.usecases.SendMessagesUseCase
import ru.chatan.app.domain.usecases.StopMessagesUseCase

@Suppress("UNCHECKED_CAST")
class ChatViewModelFactory(
    private val getMessagesUseCase: GetMessagesUseCase,
    private val sendMessagesUseCase: SendMessagesUseCase,
    private val stopMessagesUseCase: StopMessagesUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ChatViewModel::class.java)) {
            return ChatViewModel(
                getMessagesUseCase = getMessagesUseCase,
                sendMessagesUseCase = sendMessagesUseCase,
                stopMessagesUseCase = stopMessagesUseCase
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}