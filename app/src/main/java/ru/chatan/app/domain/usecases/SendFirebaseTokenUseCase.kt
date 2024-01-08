package ru.chatan.app.domain.usecases

import ru.chatan.app.domain.repository.FirebaseRepository

class SendFirebaseTokenUseCase(
    private val repository: FirebaseRepository
) {

    suspend operator fun invoke(token: String) = repository.sendToken(token = token)

}