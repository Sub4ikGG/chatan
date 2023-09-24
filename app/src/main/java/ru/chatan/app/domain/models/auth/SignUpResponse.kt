package ru.chatan.app.domain.models.auth

data class SignUpResponse(
    val name: String,
    val token: String,
    val refreshToken: String,
)
