package ru.chatan.app.data.models.auth

import kotlinx.serialization.Serializable
import ru.chatan.app.domain.models.auth.SignUpResponse

@Serializable
data class SignUpResponseDTO(
    val name: String,
    val token: String,
    val refreshToken: String,
) {
    fun toModel() = SignUpResponse(
        name = name,
        token = token,
        refreshToken = refreshToken
    )
}