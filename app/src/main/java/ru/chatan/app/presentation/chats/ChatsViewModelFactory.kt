package ru.chatan.app.presentation.chats

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.chatan.app.domain.usecases.GetChatsUseCase

@Suppress("UNCHECKED_CAST")
class ChatsViewModelFactory(private val getChatsUseCase: GetChatsUseCase) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ChatsViewModel::class.java)) {
            return ChatsViewModel(getChatsUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}