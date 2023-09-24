package ru.chatan.app.presentation.signup

import cafe.adriel.voyager.core.model.StateScreenModel
import cafe.adriel.voyager.core.model.coroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.chatan.app.UNDEFINED_ERROR
import ru.chatan.app.domain.models.auth.SignUpRequest
import ru.chatan.app.domain.usecases.SaveTokenUseCase
import ru.chatan.app.domain.usecases.SignUpUseCase

class SignUpScreenModel(
    private val signUpUseCase: SignUpUseCase,
    private val saveTokenUseCase: SaveTokenUseCase
) : StateScreenModel<SignUpState>(SignUpState.initial()) {

    fun send(event: SignUpEvent) = coroutineScope.launch(Dispatchers.IO) {
        mutableState.emit(state.value.loading())

        when (event) {
            is SignUpEvent.SignUp -> signUp(login = event.login, password = event.password)
        }
    }

    private suspend fun signUp(login: String, password: String) {
        mutableState.emit(state.value.loading())

        val response =
            signUpUseCase(signUpRequest = SignUpRequest(name = login, password = password))
        if (response.code == 200) {
            val data = response.data ?: return mutableState.emit(state.value.error(errorMessage = UNDEFINED_ERROR))
            saveToken(token = data.token, refreshToken = data.refreshToken)

            mutableState.emit(state.value.success())
        }
        else mutableState.emit(state.value.error(errorMessage = response.message))
    }

    private suspend fun saveToken(token: String, refreshToken: String) {
        saveTokenUseCase(token = token, refreshToken = refreshToken)
    }

}