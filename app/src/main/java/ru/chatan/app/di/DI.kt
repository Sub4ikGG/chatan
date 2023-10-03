package ru.chatan.app.di

import org.kodein.di.DI
import org.kodein.di.bindProvider
import org.kodein.di.bindSingleton
import org.kodein.di.instance
import ru.chatan.app.data.repository.AuthRepositoryImpl
import ru.chatan.app.data.repository.ChatsRepositoryImpl
import ru.chatan.app.domain.repository.AuthRepository
import ru.chatan.app.domain.repository.ChatsRepository
import ru.chatan.app.domain.usecases.GetChatsUseCase
import ru.chatan.app.domain.usecases.SaveTokenUseCase
import ru.chatan.app.domain.usecases.SignInAutoUseCase
import ru.chatan.app.domain.usecases.SignInUseCase
import ru.chatan.app.domain.usecases.SignUpUseCase
import ru.chatan.app.presentation.chats.ChatsViewModel
import ru.chatan.app.presentation.chats.ChatsViewModelFactory
import ru.chatan.app.presentation.signin.SignInViewModel
import ru.chatan.app.presentation.signin.SignInViewModelFactory
import ru.chatan.app.presentation.signup.SignUpViewModel
import ru.chatan.app.presentation.signup.SignUpViewModelFactory
import ru.chatan.app.presentation.start.StartViewModel
import ru.chatan.app.presentation.start.StartViewModelFactory

val repositoryDi = DI.Module("repositoryDi") {
    bindSingleton<AuthRepository> { AuthRepositoryImpl() }
    bindSingleton<ChatsRepository> { ChatsRepositoryImpl() }
}

val useCasesDi = DI.Module("useCasesDi") {
    bindProvider { SaveTokenUseCase() }

    bindProvider { SignUpUseCase() }
    bindProvider { SignInUseCase() }
    bindProvider { SignInAutoUseCase() }

    bindProvider { GetChatsUseCase() }
}

val viewModelDi = DI.Module("viewModelDi") {
    bindProvider {
        SignUpViewModelFactory(
            signUpUseCase = instance(),
            saveTokenUseCase = instance()
        ).create(SignUpViewModel::class.java)
    }

    bindProvider {
        SignInViewModelFactory(
            signInUseCase = instance(),
            saveTokenUseCase = instance()
        ).create(SignInViewModel::class.java)
    }

    bindProvider {
        StartViewModelFactory(
            signInAutoUseCase = instance()
        ).create(StartViewModel::class.java)
    }

    bindProvider {
        ChatsViewModelFactory(
            getChatsUseCase = instance()
        ).create(ChatsViewModel::class.java)
    }
}

val di: DI = DI {
    import(repositoryDi)
    import(useCasesDi)
    import(viewModelDi)
}