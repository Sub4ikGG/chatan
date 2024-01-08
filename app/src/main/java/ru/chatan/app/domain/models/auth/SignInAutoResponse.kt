package ru.chatan.app.domain.models.auth

import ru.chatan.app.domain.models.user.User

data class SignInAutoResponse(
    val user: User
)