package ru.chatan.app.presentation

import ru.chatan.app.domain.models.chat.Chat
import ru.chatan.app.domain.models.chat.ChatUser
import ru.chatan.app.domain.models.message.ChatMessage

val sampleChats = listOf(
    Chat(
        chatId = 0,
        name = "Alan Walker",
    ),
    Chat(
        chatId = 0,
        name = "Alan Walker",
    ),
    Chat(
        chatId = 0,
        name = "Alan Walker",
    ),
    Chat(
        chatId = 0,
        name = "Alan Walker",
    )
)

val sampleChatMessages = listOf(
    ChatMessage(
        id = 0,
        user = ChatUser(id = 0, name = "Alan Walker"),
        body = "Привет, макаки!",
        date = System.currentTimeMillis()
    ),
    ChatMessage(
        id = 0,
        user = ChatUser(id = 0, name = "Alan Walker2"),
        body = "Привет, макаки!",
        date = System.currentTimeMillis()
    ),
    ChatMessage(
        id = 0,
        user = ChatUser(id = 0, name = "Alan Walker3"),
        body = "Привет, макаки!",
        date = System.currentTimeMillis()
    ),
    ChatMessage(
        id = 0,
        user = ChatUser(id = 0, name = "Alan Walker4"),
        body = "Привет, макаки!",
        date = System.currentTimeMillis()
    ),
    ChatMessage(
        id = 0,
        user = ChatUser(id = 0, name = "Alan Walker5"),
        body = "Привет, макаки!",
        date = System.currentTimeMillis()
    )
).reversed()