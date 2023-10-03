package ru.chatan.app.presentation.start

sealed class StartEvent {
    object SignInAuto : StartEvent()
}