package ru.chatan.app.data.models.auth

import kotlinx.serialization.Serializable

@Serializable
data class SignUpResponseDTO(
    val login: String,
    val password: String
)