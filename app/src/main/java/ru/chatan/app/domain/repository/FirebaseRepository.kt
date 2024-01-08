package ru.chatan.app.domain.repository

import ru.efremovkirill.ktorhandler.Response

interface FirebaseRepository {

    suspend fun sendToken(token: String): Response<String?>

}