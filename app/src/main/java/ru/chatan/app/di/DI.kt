package ru.chatan.app.di

import org.kodein.di.DI
import org.kodein.di.bindProvider
import org.kodein.di.bindSingleton
import ru.chatan.app.data.repository.AuthRepositoryImpl
import ru.chatan.app.domain.repository.AuthRepository
import ru.chatan.app.presentation.signin.SignInScreenModel

val di = DI {
    bindProvider { SignInScreenModel() }
    bindSingleton<AuthRepository> { AuthRepositoryImpl() }
}