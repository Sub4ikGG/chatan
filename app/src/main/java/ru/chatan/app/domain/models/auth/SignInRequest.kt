package ru.chatan.app.domain.models.auth

import ru.chatan.app.data.models.auth.SignInRequestDTO

data class SignInRequest(
    val login: String,
    val password: String
) {
    fun toDTO(): SignInRequestDTO =
        SignInRequestDTO(
            login = login,
            password = password
        )
}
