package ru.chatan.app.domain.models.auth

import ru.chatan.app.data.models.auth.SignUpRequestDTO

data class SignUpRequest(
    val login: String,
    val password: String
) {
    fun toDTO(): SignUpRequestDTO =
        SignUpRequestDTO(
            login = login,
            password = password
        )
}
