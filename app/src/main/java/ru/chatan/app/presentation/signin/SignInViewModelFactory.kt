package ru.chatan.app.presentation.signin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.chatan.app.domain.usecases.SaveTokenUseCase
import ru.chatan.app.domain.usecases.SignInUseCase

@Suppress("UNCHECKED_CAST")
class SignInViewModelFactory(private val signInUseCase: SignInUseCase, private val saveTokenUseCase: SaveTokenUseCase) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SignInViewModel::class.java)) {
            return SignInViewModel(signInUseCase = signInUseCase, saveTokenUseCase = saveTokenUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}