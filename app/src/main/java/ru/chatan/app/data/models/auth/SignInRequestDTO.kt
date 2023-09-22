package ru.chatan.app.data.models.auth

import kotlinx.serialization.Serializable

@Serializable
data class SignInRequestDTO(
    val login: String,
    val password: String
)
