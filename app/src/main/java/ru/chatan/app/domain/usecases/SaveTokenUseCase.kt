package ru.chatan.app.domain.usecases

import org.kodein.di.instance
import ru.chatan.app.di.di
import ru.chatan.app.domain.repository.AuthRepository

class SaveTokenUseCase {
    private val repository: AuthRepository by di.instance()

    suspend operator fun invoke(token: String, refreshToken: String) =
        repository.saveToken(token = token, refreshToken = refreshToken)
}