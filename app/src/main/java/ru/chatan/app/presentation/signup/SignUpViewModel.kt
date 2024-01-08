package ru.chatan.app.presentation.signup

import ru.chatan.app.ChatanApplication
import ru.chatan.app.StateViewModel
import ru.chatan.app.UNDEFINED_ERROR
import ru.chatan.app.domain.models.auth.SignUpRequest
import ru.chatan.app.domain.usecases.SaveTokenUseCase
import ru.chatan.app.domain.usecases.SendFirebaseTokenUseCase
import ru.chatan.app.domain.usecases.SignUpUseCase
import ru.chatan.app.presentation.User
import ru.efremovkirill.localstorage.LocalStorage

class SignUpViewModel(
    private val signUpUseCase: SignUpUseCase,
    private val saveTokenUseCase: SaveTokenUseCase,
    private val sendFirebaseTokenUseCase: SendFirebaseTokenUseCase
) : StateViewModel<SignUpState, SignUpEvent>(SignUpState.initial()) {

    override fun send(event: SignUpEvent) = run {
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

            User.user = data.user

            sendFirebaseToken()
            mutableState.emit(state.value.success())
        }
        else mutableState.emit(state.value.error(errorMessage = response.message))
    }

    private suspend fun sendFirebaseToken() {
        val token = LocalStorage.newInstance().get(ChatanApplication.FIREBASE_TOKEN).orEmpty()
        sendFirebaseTokenUseCase(token = token)
    }

    private suspend fun saveToken(token: String, refreshToken: String) {
        saveTokenUseCase(token = token, refreshToken = refreshToken)
    }

}