package ru.chatan.app.data.models.message

import kotlinx.serialization.Serializable

@Serializable
data class SendMessageDTO(
    val body: String
)
