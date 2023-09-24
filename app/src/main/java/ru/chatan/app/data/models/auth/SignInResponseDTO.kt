package ru.chatan.app.data.models.auth

import kotlinx.serialization.Serializable
import ru.chatan.app.domain.models.auth.SignInResponse
import ru.efremovkirill.ktorhandler.BaseResponse

@Serializable
data class SignInResponseDTO(
    val name: String,
    val token: String,
    val refreshToken: String
) : BaseResponse<SignInResponse> {
    override fun toModel() =
        SignInResponse(
            name = name,
            token = token,
            refreshToken = refreshToken
        )
}
