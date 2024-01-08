package ru.chatan.app.data.repository

import ru.chatan.app.data.models.firebase.FirebaseTokenDTO
import ru.chatan.app.domain.repository.FirebaseRepository
import ru.efremovkirill.ktorhandler.KtorClient
import ru.efremovkirill.ktorhandler.Response

class FirebaseRepositoryImpl : FirebaseRepository {

    override suspend fun sendToken(token: String): Response<String?> =
        KtorClient.postSafely(
            path = SEND_TOKEN_PATH,
            body = FirebaseTokenDTO(token = token)
        )

    private companion object {
        const val SEND_TOKEN_PATH = "/firebase/send"
    }

}