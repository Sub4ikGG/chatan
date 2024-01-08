package ru.chatan.app.presentation.createchat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.chatan.app.domain.usecases.CreateChatUseCase

@Suppress("UNCHECKED_CAST")
class CreateChatViewModelFactory(private val createChatUseCase: CreateChatUseCase) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CreateChatViewModel::class.java)) {
            return CreateChatViewModel(createChatUseCase = createChatUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}