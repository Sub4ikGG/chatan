package ru.chatan.app.data.models.auth

import kotlinx.serialization.Serializable

@Serializable
data class SignUpRequestDTO(
    val name: String,
    val password: String
)