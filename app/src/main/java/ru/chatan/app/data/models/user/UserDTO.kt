package ru.chatan.app.data.models.user

import kotlinx.serialization.Serializable
import ru.chatan.app.domain.models.user.User

@Serializable
data class UserDTO(
    val id: Long,
    val name: String,
    val avatar: UserAvatarDTO
) {
    fun toModel() = User(
        id = id,
        name = name,
        avatar = avatar.toModel()
    )
}
