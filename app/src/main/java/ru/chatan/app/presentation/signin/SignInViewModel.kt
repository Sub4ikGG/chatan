package ru.chatan.app.presentation.signin

import ru.chatan.app.DATA_ERROR
import ru.chatan.app.StateViewModel
import ru.chatan.app.domain.models.auth.SignInRequest
import ru.chatan.app.domain.usecases.SaveTokenUseCase
import ru.chatan.app.domain.usecases.SignInUseCase

class SignInViewModel(
    private val signInUseCase: SignInUseCase,
    private val saveTokenUseCase: SaveTokenUseCase
): StateViewModel<SignInState, SignInEvent>(SignInState.initial()) {

    override fun send(event: SignInEvent) = run {
        when (event) {
            is SignInEvent.SignIn -> signIn(
                login = event.login,
                password = event.password
            )
        }
    }

    private suspend fun signIn(login: String, password: String) {
        mutableState.emit(state.value.loading())

        val response =
            signInUseCase(signInRequest = SignInRequest(name = login, password = password))
        if (response.code == 200) {
            val data = response.data ?: return mutableState.emit(state.value.error(errorMessage = DATA_ERROR))
            saveToken(token = data.token, refreshToken = data.refreshToken)

            mutableState.emit(state.value.success())
        }
        else mutableState.emit(state.value.error(errorMessage = response.message))
    }

    private suspend fun saveToken(token: String, refreshToken: String) {
        saveTokenUseCase(token = token, refreshToken = refreshToken)
    }

}