package ru.chatan.app.domain.models.messages

data class UserMessage(
    val id: Long,
    val name: String,
    val message: String,
    val date: String
)
