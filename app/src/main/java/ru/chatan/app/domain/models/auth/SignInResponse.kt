package ru.chatan.app.domain.models.auth

import ru.chatan.app.domain.models.user.User

data class SignInResponse(
    val user: User,
    val token: String,
    val refreshToken: String
)
