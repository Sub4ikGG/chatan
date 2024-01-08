package ru.chatan.app.data.models.chat

import kotlinx.serialization.Serializable

@Serializable
data class CreateChatDTO(
    val code: String,
    val name: String,
    val description: String,
    val userLimit: Int
)
