package ru.chatan.app.presentation.start

import ru.chatan.app.StateViewModel
import ru.chatan.app.domain.usecases.SignInAutoUseCase
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

    suspend fun signInAuto() {
        val localStorage = LocalStorage.newInstance()
        if (!localStorage.has(LocalStorage.REFRESH_TOKEN)) return mutableState.emit(state.value.cantSignIn())

        val response = signInAutoUseCase()
        if (response.isSuccess())
            mutableState.emit(state.value.signInSuccess())
        else mutableState.emit(state.value.error(error = response.message))
    }

}