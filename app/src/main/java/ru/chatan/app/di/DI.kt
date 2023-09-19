package ru.chatan.app.di

import org.kodein.di.DI
import org.kodein.di.bindProvider
import ru.chatan.app.presentation.signin.SignInScreenModel

val di = DI {
    bindProvider { SignInScreenModel() }
}