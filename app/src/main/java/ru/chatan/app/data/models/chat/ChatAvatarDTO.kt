package ru.chatan.app.data.models.chat

import kotlinx.serialization.Serializable
import ru.chatan.app.domain.models.chat.ChatAvatar

@Serializable
data class ChatAvatarDTO(
    val href: String
) {
    fun toModel() = ChatAvatar(
        href = href
    )
}
