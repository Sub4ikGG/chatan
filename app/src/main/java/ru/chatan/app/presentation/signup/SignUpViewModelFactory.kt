package ru.chatan.app.presentation.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.chatan.app.domain.usecases.SaveTokenUseCase
import ru.chatan.app.domain.usecases.SignUpUseCase

@Suppress("UNCHECKED_CAST")
class SignUpViewModelFactory(private val signUpUseCase: SignUpUseCase, private val saveTokenUseCase: SaveTokenUseCase) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SignUpViewModel::class.java)) {
            return SignUpViewModel(signUpUseCase = signUpUseCase, saveTokenUseCase = saveTokenUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}