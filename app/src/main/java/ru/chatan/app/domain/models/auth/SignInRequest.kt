package ru.chatan.app.domain.models.auth

import ru.chatan.app.data.models.auth.SignInRequestDTO

data class SignInRequest(
    val name: String,
    val password: String
) {
    fun toDTO(): SignInRequestDTO =
        SignInRequestDTO(
            name = name,
            password = password
        )
}
