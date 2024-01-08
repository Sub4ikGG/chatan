package ru.chatan.app.data.models.chat

import kotlinx.serialization.Serializable
import ru.chatan.app.data.models.user.UserAvatarDTO
import ru.chatan.app.domain.models.chat.ChatUser

@Serializable
data class ChatUserDTO(
    val id: Long,
    val name: String,
    val avatar: UserAvatarDTO,
) {
    fun toModel() = ChatUser(
        id = id,
        name = name,
        avatar = avatar.toModel()
    )
}