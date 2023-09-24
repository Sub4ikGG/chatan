package ru.chatan.app.domain.models.auth

import ru.chatan.app.data.models.auth.SignUpRequestDTO

data class SignUpRequest(
    val name: String,
    val password: String
) {
    fun toDTO(): SignUpRequestDTO =
        SignUpRequestDTO(
            name = name,
            password = password
        )
}
