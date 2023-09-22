package ru.chatan.app.data.models.auth

import kotlinx.serialization.Serializable
import ru.chatan.app.domain.models.auth.SignInResponse
import ru.efremovkirill.ktorhandler.BaseResponse

@Serializable
data class SignInResponseDTO(
    val login: String,
    val password: String
) : BaseResponse<SignInResponse> {
    override fun toModel() =
        SignInResponse(login = login, password = password)
}
