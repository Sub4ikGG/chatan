package ru.chatan.app.data.models.user

import kotlinx.serialization.Serializable
import ru.chatan.app.domain.models.user.UserAvatar

@Serializable
data class UserAvatarDTO(
    val href: String
) {
    fun toModel() = UserAvatar(
        href = href
    )
}