package ru.chatan.app.data.models.chat

import kotlinx.serialization.Serializable
import ru.chatan.app.domain.models.chat.Chat

@Serializable
data class ChatDTO(
    val id: Long,
    val code: String,
    val description: String,
    val name: String,
    val avatar: ChatAvatarDTO
) {
    fun toModel() = Chat(
        id = id,
        name = name,
        description = description,
        code = code,
        avatar = avatar.toModel()
    )
}
