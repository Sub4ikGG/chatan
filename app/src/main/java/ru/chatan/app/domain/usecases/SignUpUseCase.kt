package ru.chatan.app.domain.usecases

import org.kodein.di.instance
import ru.chatan.app.data.models.auth.SignUpResponseDTO
import ru.chatan.app.di.di
import ru.chatan.app.domain.models.auth.SignUpRequest
import ru.chatan.app.domain.models.auth.SignUpResponse
import ru.chatan.app.domain.repository.AuthRepository
import ru.efremovkirill.ktorhandler.Response

class SignUpUseCase {
    private val repository: AuthRepository by di.instance()

    suspend operator fun invoke(signUpRequest: SignUpRequest): Response<SignUpResponse?> =
        repository.signUp(signUpRequest = signUpRequest).map()

    private fun Response<SignUpResponseDTO?>.map(): Response<SignUpResponse?> =
        Response(
            code = this.code,
            responseMessage = this.responseMessage,
            data = this.data?.toModel(),
            errors = this.errors
        )
}