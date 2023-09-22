package ru.chatan.app.data.models.auth

import kotlinx.serialization.Serializable

@Serializable
data class SignUpRequestDTO(
    val login: String,
    val password: String
)
