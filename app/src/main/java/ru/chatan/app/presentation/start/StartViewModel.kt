package ru.chatan.app.presentation.start

import android.util.Log
import ru.chatan.app.ChatanApplication
import ru.chatan.app.DATA_ERROR
import ru.chatan.app.StateViewModel
import ru.chatan.app.domain.usecases.SendFirebaseTokenUseCase
import ru.chatan.app.domain.usecases.SignInAutoUseCase
import ru.chatan.app.presentation.User
import ru.efremovkirill.localstorage.LocalStorage

class StartViewModel(
    private val signInAutoUseCase: SignInAutoUseCase,
    private val sendFirebaseTokenUseCase: SendFirebaseTokenUseCase
): StateViewModel<StartState, StartEvent>(StartState.initial()) {

    private val localStorage: LocalStorage by lazy { LocalStorage.newInstance() }

    init {
        val deviceId = localStorage.get("deviceId")
        val refreshToken = localStorage.get(LocalStorage.REFRESH_TOKEN)
        Log.d("StartScreen", "deviceId: $deviceId\nrefreshToken: $refreshToken")

        send(event = StartEvent.SignInAuto)
    }

    override fun send(event: StartEvent) = run {
        when (event) {
            StartEvent.SignInAuto -> signInAuto()
        }
    }

    private suspend fun signInAuto() {
        if (!localStorage.has(LocalStorage.REFRESH_TOKEN)) return mutableState.emit(state.value.cantSignIn())

        val response = signInAutoUseCase()
        if (response.isSuccess()) {
            val data = response.data ?: return mutableState.emit(state.value.error(error = DATA_ERROR))

            User.user = data.user

            sendFirebaseToken()
            mutableState.emit(state.value.signInSuccess())
        }
        else mutableState.emit(state.value.error(error = response.message))
    }

    private suspend fun sendFirebaseToken() {
        val token = localStorage.get(ChatanApplication.FIREBASE_TOKEN).orEmpty()
        sendFirebaseTokenUseCase(token = token)
    }

}