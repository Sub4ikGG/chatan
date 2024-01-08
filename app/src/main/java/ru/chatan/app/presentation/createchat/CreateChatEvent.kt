package ru.chatan.app.presentation.createchat

sealed class CreateChatEvent {
    class CreateChat(val chat: ru.chatan.app.domain.models.chat.CreateChat): CreateChatEvent()
}
