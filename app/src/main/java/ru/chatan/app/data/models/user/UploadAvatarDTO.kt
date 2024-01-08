package ru.chatan.app.data.models.user

import kotlinx.serialization.Serializable

@Serializable
data class UploadAvatarDTO(
    val base64: String
)
