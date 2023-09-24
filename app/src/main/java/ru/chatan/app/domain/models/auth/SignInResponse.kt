package ru.chatan.app.domain.models.auth

data class SignInResponse(
    val name: String,
    val token: String,
    val refreshToken: String
)
