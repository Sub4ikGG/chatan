package ru.chatan.app.presentation.chat

sealed class ChatEvent {
    class GetMessages(val chatId: Long) : ChatEvent()
    class SendMessage(val message: ru.chatan.app.domain.models.message.SendMessage) : ChatEvent()
}
