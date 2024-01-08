package ru.chatan.app.domain.models.auth

import ru.chatan.app.domain.models.user.User

data class SignUpResponse(
    val user: User,
    val token: String,
    val refreshToken: String,
)
