package ru.chatan.app.presentation.start

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.chatan.app.domain.usecases.SendFirebaseTokenUseCase
import ru.chatan.app.domain.usecases.SignInAutoUseCase

@Suppress("UNCHECKED_CAST")
class StartViewModelFactory(
    private val signInAutoUseCase: SignInAutoUseCase,
    private val sendFirebaseTokenUseCase: SendFirebaseTokenUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(StartViewModel::class.java)) {
            return StartViewModel(
                signInAutoUseCase = signInAutoUseCase,
                sendFirebaseTokenUseCase = sendFirebaseTokenUseCase
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}