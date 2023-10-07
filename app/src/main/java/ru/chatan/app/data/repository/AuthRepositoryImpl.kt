package ru.chatan.app.data.repository

import ru.chatan.app.data.models.auth.SignInAutoResponseDTO
import ru.chatan.app.data.models.auth.SignInResponseDTO
import ru.chatan.app.data.models.auth.SignUpResponseDTO
import ru.chatan.app.domain.models.auth.SignInRequest
import ru.chatan.app.domain.models.auth.SignUpRequest
import ru.chatan.app.domain.repository.AuthRepository
import ru.efremovkirill.ktorhandler.KtorClient
import ru.efremovkirill.ktorhandler.Response
import ru.efremovkirill.ktorhandler.tokens.RefreshTokenDTO
import ru.efremovkirill.localstorage.LocalStorage

class AuthRepositoryImpl: AuthRepository {
    private val localStorage by lazy {
        LocalStorage.newInstance()
    }

    override suspend fun signIn(signInRequest: SignInRequest): Response<SignInResponseDTO?> =
        KtorClient.postSafely(
            path = SIGN_IN_PATH,
            body = signInRequest.toDTO()
        )

    override suspend fun signUp(signUpRequest: SignUpRequest): Response<SignUpResponseDTO?> =
        KtorClient.postSafely(
            path = SIGN_UP_PATH,
            body = signUpRequest.toDTO()
        )

    override suspend fun signInAuto(): Response<SignInAutoResponseDTO?> {
        val refreshToken = localStorage.get(key = LocalStorage.REFRESH_TOKEN).orEmpty()
        return KtorClient.postSafely(
            path = SIGN_IN_AUTO_PATH,
            body = RefreshTokenDTO(refreshToken = refreshToken)
        )
    }

    override suspend fun saveToken(token: String, refreshToken: String) {
        localStorage.save(LocalStorage.TOKEN, token)
        localStorage.save(LocalStorage.REFRESH_TOKEN, refreshToken)
    }

    private companion object {
        const val SIGN_IN_PATH = "/sign-in"
        const val SIGN_IN_AUTO_PATH = "/sign-in-auto"
        const val SIGN_UP_PATH = "/sign-up"
    }
}