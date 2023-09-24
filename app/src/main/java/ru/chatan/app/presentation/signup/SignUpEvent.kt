package ru.chatan.app.presentation.signup

sealed class SignUpEvent {

    class SignUp(val login: String, val password: String) : SignUpEvent()

}