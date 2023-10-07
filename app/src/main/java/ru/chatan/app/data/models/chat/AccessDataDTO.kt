package ru.chatan.app.data.models.chat

import kotlinx.serialization.Serializable

@Serializable
data class AccessDataDTO(
    val deviceId: String,
    val token: String,
    val chatId: String
)