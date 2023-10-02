package ru.chatan.app.domain.usecases

import org.kodein.di.instance
import ru.chatan.app.data.models.auth.SignInResponseDTO
import ru.chatan.app.di.di
import ru.chatan.app.domain.models.auth.SignInRequest
import ru.chatan.app.domain.models.auth.SignInResponse
import ru.chatan.app.domain.repository.AuthRepository
import ru.efremovkirill.ktorhandler.Response

class SignInUseCase {
    private val repository: AuthRepository by di.instance()

    suspend operator fun invoke(signInRequest: SignInRequest): Response<SignInResponse?> =
        repository.signIn(signInRequest = signInRequest).map()

    private fun Response<SignInResponseDTO?>.map(): Response<SignInResponse?> =
        Response(
            code = this.code,
            responseMessage = this.message,
            data = this.data?.toModel(),
            errors = this.errors
        )
}