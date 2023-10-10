package ru.chatan.app.data.models.chat

import kotlinx.serialization.Serializable

@Serializable
data class ConnectChatDTO(
    val code: String
)