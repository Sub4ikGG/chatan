package ru.chatan.app.data.repository

import ru.chatan.app.data.models.auth.SignInResponseDTO
import ru.chatan.app.data.models.auth.SignUpResponseDTO
import ru.chatan.app.domain.models.auth.SignInRequest
import ru.chatan.app.domain.models.auth.SignUpRequest
import ru.chatan.app.domain.repository.AuthRepository
import ru.efremovkirill.ktorhandler.KtorClient
import ru.efremovkirill.ktorhandler.Response

class AuthRepositoryImpl: AuthRepository {
    override suspend fun signIn(signInRequest: SignInRequest): Response<SignInResponseDTO?> =
        KtorClient.postSafely(
            path = SIGN_IN_PATH,
            body = signInRequest.toDTO()
        ) ?: Response.empty()

    override suspend fun signUp(signUpRequest: SignUpRequest): Response<SignUpResponseDTO?> =
        KtorClient.postSafely(
            path = SIGN_UP_PATH,
            body = signUpRequest.toDTO()
        ) ?: Response.empty()

    private companion object {
        const val SIGN_IN_PATH = "/sign-in"
        const val SIGN_UP_PATH = "/sign-up"
    }
}