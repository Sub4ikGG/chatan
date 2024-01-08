package ru.chatan.app.domain.models.message

import ru.chatan.app.domain.models.user.User

data class ChatMessage(
    val id: Long,
    val user: User?,
    val body: String,
    val date: Long
)
