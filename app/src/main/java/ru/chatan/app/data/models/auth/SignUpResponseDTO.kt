package ru.chatan.app.data.models.auth

import kotlinx.serialization.Serializable
import ru.chatan.app.data.models.user.UserDTO
import ru.chatan.app.domain.models.auth.SignUpResponse

@Serializable
data class SignUpResponseDTO(
    val user: UserDTO,
    val token: String,
    val refreshToken: String,
) {
    fun toModel() = SignUpResponse(
        user = user.toModel(),
        token = token,
        refreshToken = refreshToken
    )
}