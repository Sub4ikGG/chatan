package ru.chatan.app.presentation.chats

sealed class ChatsEvent {
    object GetChats : ChatsEvent()
}
