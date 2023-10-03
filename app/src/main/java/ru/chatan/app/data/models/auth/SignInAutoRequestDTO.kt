package ru.chatan.app.data.models.auth

import kotlinx.serialization.Serializable

@Serializable
data class SignInAutoRequestDTO(
    val refreshToken: String
)