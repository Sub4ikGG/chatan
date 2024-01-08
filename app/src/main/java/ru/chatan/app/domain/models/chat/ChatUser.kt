package ru.chatan.app.domain.models.chat

import ru.chatan.app.domain.models.user.UserAvatar

data class ChatUser(
    val id: Long,
    val avatar: UserAvatar,
    val name: String
)
