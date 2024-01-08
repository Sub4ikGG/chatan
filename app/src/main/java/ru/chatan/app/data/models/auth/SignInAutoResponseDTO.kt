package ru.chatan.app.data.models.auth

import kotlinx.serialization.Serializable
import ru.chatan.app.data.models.user.UserDTO
import ru.chatan.app.domain.models.auth.SignInAutoResponse

@Serializable
data class SignInAutoResponseDTO(
    val user: UserDTO
) {
    fun toModel() = SignInAutoResponse(
        user = user.toModel()
    )
}