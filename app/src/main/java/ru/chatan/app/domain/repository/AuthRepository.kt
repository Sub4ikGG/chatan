package ru.chatan.app.domain.repository

import ru.chatan.app.data.models.auth.SignInAutoResponseDTO
import ru.chatan.app.data.models.auth.SignInResponseDTO
import ru.chatan.app.data.models.auth.SignUpResponseDTO
import ru.chatan.app.domain.models.auth.SignInRequest
import ru.chatan.app.domain.models.auth.SignUpRequest
import ru.efremovkirill.ktorhandler.Response

interface AuthRepository {

    suspend fun signIn(signInRequest: SignInRequest): Response<SignInResponseDTO?>
    suspend fun signUp(signUpRequest: SignUpRequest): Response<SignUpResponseDTO?>
    suspend fun signInAuto(): Response<SignInAutoResponseDTO?>
    suspend fun saveToken(token: String, refreshToken: String)

}