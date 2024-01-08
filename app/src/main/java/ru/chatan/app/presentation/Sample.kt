package ru.chatan.app.presentation

import ru.chatan.app.domain.models.chat.Chat
import ru.chatan.app.domain.models.chat.ChatAvatar
import ru.chatan.app.domain.models.message.ChatMessage
import ru.chatan.app.domain.models.user.User
import ru.chatan.app.domain.models.user.UserAvatar

val sampleChats = listOf(
    Chat(
        id = 0,
        name = "Alan Walker",
        description = "",
        code = "",
        avatar = ChatAvatar("")
    ),
    Chat(
        id = 0,
        name = "Alan Walker",
        description = "",
        code = "",
        avatar = ChatAvatar("")
    ),
    Chat(
        id = 0,
        name = "Alan Walker",
        description = "",
        code = "",
        avatar = ChatAvatar("")
    ),
    Chat(
        id = 0,
        name = "Alan Walker",
        description = "",
        code = "",
        avatar = ChatAvatar("")
    )
)

val sampleChatMessages = listOf(
    ChatMessage(
        id = 0,
        user = User(id = 0, name = "Alan Walker", avatar = UserAvatar("")),
        body = "Привет, макаки!",
        date = System.currentTimeMillis()
    ),
    ChatMessage(
        id = 0,
        user = User(id = 0, name = "Alan Walker", avatar = UserAvatar("")),
        body = "Привет, макаки!",
        date = System.currentTimeMillis()
    ),
    ChatMessage(
        id = 0,
        user = User(id = 0, name = "Alan Walker", avatar = UserAvatar("")),
        body = "Привет, макаки!",
        date = System.currentTimeMillis()
    ),
    ChatMessage(
        id = 0,
        user = User(id = 0, name = "Alan Walker", avatar = UserAvatar("")),
        body = "Привет, макаки!",
        date = System.currentTimeMillis()
    ),
    ChatMessage(
        id = 0,
        user = User(id = 0, name = "Alan Walker", avatar = UserAvatar("")),
        body = "Привет, макаки!",
        date = System.currentTimeMillis()
    )
).reversed()