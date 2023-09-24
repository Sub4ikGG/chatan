package ru.chatan.app.di

import org.kodein.di.DI
import org.kodein.di.bindProvider
import org.kodein.di.bindSingleton
import org.kodein.di.instance
import ru.chatan.app.data.repository.AuthRepositoryImpl
import ru.chatan.app.domain.repository.AuthRepository
import ru.chatan.app.domain.usecases.SaveTokenUseCase
import ru.chatan.app.domain.usecases.SignInUseCase
import ru.chatan.app.domain.usecases.SignUpUseCase
import ru.chatan.app.presentation.signin.SignInScreenModel
import ru.chatan.app.presentation.signup.SignUpScreenModel

val di = DI {
    bindProvider { SaveTokenUseCase() }
    bindProvider { SignUpUseCase() }
    bindProvider { SignInUseCase() }

    bindProvider {
        val signUpUseCase: SignUpUseCase by di.instance()
        val saveTokenUseCase: SaveTokenUseCase by di.instance()

        SignUpScreenModel(
            signUpUseCase = signUpUseCase,
            saveTokenUseCase = saveTokenUseCase)
    }

    bindProvider {
        val signInUseCase: SignInUseCase by di.instance()
        val saveTokenUseCase: SaveTokenUseCase by di.instance()

        SignInScreenModel(
            signInUseCase = signInUseCase,
            saveTokenUseCase = saveTokenUseCase
        )
    }

    bindSingleton<AuthRepository> { AuthRepositoryImpl() }
}