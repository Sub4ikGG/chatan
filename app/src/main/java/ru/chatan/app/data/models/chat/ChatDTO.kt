package ru.chatan.app.data.models.chat

import kotlinx.serialization.Serializable
import ru.chatan.app.domain.models.chat.Chat

@Serializable
data class ChatDTO(
    val chatId: Long,
    val name: String
) {
    fun toModel() = Chat(
        chatId = chatId,
        name = name
    )
}
