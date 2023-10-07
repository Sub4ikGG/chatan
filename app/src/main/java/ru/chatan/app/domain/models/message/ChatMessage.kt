package ru.chatan.app.domain.models.message

import ru.chatan.app.domain.models.chat.ChatUser

data class ChatMessage(
    val id: Long,
    val user: ChatUser?,
    val body: String,
    val date: Long
)
