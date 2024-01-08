package ru.chatan.app.data.models.message

import kotlinx.serialization.Serializable
import ru.chatan.app.data.models.user.UserDTO
import ru.chatan.app.domain.models.message.ChatMessage

@Serializable
data class ChatMessageDTO(
    val id: Long,
    val user: UserDTO? = null,
    val body: String,
    val date: Long
) {
    fun toModel() = ChatMessage(
        id = id,
        user = user?.toModel(),
        body = body,
        date = date
    )
}
