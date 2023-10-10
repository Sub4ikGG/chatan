package ru.chatan.app.presentation.connectchat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.chatan.app.domain.usecases.ConnectChatUseCase

@Suppress("UNCHECKED_CAST")
class ConnectChatViewModelFactory(private val connectChatUseCase: ConnectChatUseCase) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ConnectChatViewModel::class.java)) {
            return ConnectChatViewModel(connectChatUseCase = connectChatUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}