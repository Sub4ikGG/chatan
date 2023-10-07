package ru.chatan.app.presentation.start

import android.util.Log
import ru.chatan.app.DATA_ERROR
import ru.chatan.app.StateViewModel
import ru.chatan.app.domain.usecases.SignInAutoUseCase
import ru.chatan.app.presentation.User
import ru.efremovkirill.localstorage.LocalStorage


class StartViewModel(
    private val signInAutoUseCase: SignInAutoUseCase
): StateViewModel<StartState, StartEvent>(StartState.initial()) {

    init {
        send(event = StartEvent.SignInAuto)
    }

    override fun send(event: StartEvent) = run {
        when (event) {
            StartEvent.SignInAuto -> signInAuto()
        }
    }

    private suspend fun signInAuto() {
        val localStorage = LocalStorage.newInstance()
        if (!localStorage.has(LocalStorage.REFRESH_TOKEN)) return mutableState.emit(state.value.cantSignIn())

        Log.d("StartViewModel", "deviceId: ${localStorage.get("deviceId")}")
        Log.d("StartViewModel", "refreshToken: ${localStorage.get(LocalStorage.REFRESH_TOKEN)}")

        val response = signInAutoUseCase()
        if (response.isSuccess()) {
            val data = response.data ?: return mutableState.emit(state.value.error(error = DATA_ERROR))

            User.name = data.name

            mutableState.emit(state.value.signInSuccess())
        }
        else mutableState.emit(state.value.error(error = response.message))
    }

}