package ru.chatan.app.data.models.auth

import kotlinx.serialization.Serializable
import ru.chatan.app.domain.models.auth.SignInAutoResponse

@Serializable
data class SignInAutoResponseDTO(
    val name: String
) {
    fun toModel() = SignInAutoResponse(
        name = name
    )
}