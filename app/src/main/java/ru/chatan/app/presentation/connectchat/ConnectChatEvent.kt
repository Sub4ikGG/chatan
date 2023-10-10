package ru.chatan.app.presentation.connectchat

sealed class ConnectChatEvent {
    class Connect(val code: String) : ConnectChatEvent()
}
