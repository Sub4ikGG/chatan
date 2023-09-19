package ru.chatan.app.presentation.signin

import cafe.adriel.voyager.core.model.StateScreenModel
import cafe.adriel.voyager.core.model.coroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SignInScreenModel: StateScreenModel<SignInState>(SignInState.initial()) {

    fun send(event: SignInEvent) = coroutineScope.launch(Dispatchers.IO) {
        mutableState.emit(state.value.loading())

        when (event) {
            is SignInEvent.SignIn -> signIn(login = event.login, password = event.password)
        }
    }

    private suspend fun signIn(login: String, password: String) {

    }

}