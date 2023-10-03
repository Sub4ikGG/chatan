package ru.chatan.app.domain.usecases

import org.kodein.di.instance
import ru.chatan.app.data.models.auth.SignInAutoResponseDTO
import ru.chatan.app.di.di
import ru.chatan.app.domain.models.auth.SignInAutoResponse
import ru.chatan.app.domain.repository.AuthRepository
import ru.efremovkirill.ktorhandler.Response

class SignInAutoUseCase {
    private val repository: AuthRepository by di.instance()

    suspend operator fun invoke(): Response<SignInAutoResponse?> =
        repository.signInAuto().map()

    private fun Response<SignInAutoResponseDTO?>.map(): Response<SignInAutoResponse?> =
        Response(
            code = this.code,
            responseMessage = this.message,
            data = this.data?.toModel(),
            errors = this.errors
        )
}