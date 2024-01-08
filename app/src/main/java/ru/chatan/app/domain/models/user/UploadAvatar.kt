package ru.chatan.app.domain.models.user

import ru.chatan.app.data.models.user.UploadAvatarDTO

data class UploadAvatar(
    val base64: String
) {
    fun toDTO() = UploadAvatarDTO(
        base64 = base64
    )
}