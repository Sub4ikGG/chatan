package ru.chatan.app.domain.models.chat

data class Chat(
    val id: Long,
    val name: String,
    val description: String,
    val code: String,
    val avatar: ChatAvatar
)
