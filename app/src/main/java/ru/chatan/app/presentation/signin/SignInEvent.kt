package ru.chatan.app.presentation.signin

sealed class SignInEvent {

    class SignIn(val login: String, val password: String) : SignInEvent()

}