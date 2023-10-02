package ru.chatan.app.di

import org.kodein.di.DI
import org.kodein.di.bindProvider
import org.kodein.di.bindSingleton
import org.kodein.di.instance
import ru.chatan.app.data.repository.AuthRepositoryImpl
import ru.chatan.app.domain.repository.AuthRepository
import ru.chatan.app.domain.usecases.GetChatsUseCase
import ru.chatan.app.domain.usecases.SaveTokenUseCase
import ru.chatan.app.domain.usecases.SignInUseCase
import ru.chatan.app.domain.usecases.SignUpUseCase
import ru.chatan.app.presentation.chats.ChatsViewModel
import ru.chatan.app.presentation.chats.ChatsViewModelFactory
import ru.chatan.app.presentation.signin.SignInViewModel
import ru.chatan.app.presentation.signin.SignInViewModelFactory
import ru.chatan.app.presentation.signup.SignUpViewModel
import ru.chatan.app.presentation.signup.SignUpViewModelFactory

val repositoryDi = DI.Module("repositoryDi") {
    bindSingleton<AuthRepository> { AuthRepositoryImpl() }
}

val useCasesDi = DI.Module("useCasesDi") {
    bindProvider { SaveTokenUseCase() }
    bindProvider { SignUpUseCase() }
    bindProvider { SignInUseCase() }
    bindProvider { GetChatsUseCase() }
}

val viewModelDi = DI.Module("viewModelDi") {
    bindProvider {
        val signUpUseCase: SignUpUseCase by di.instance()
        val saveTokenUseCase: SaveTokenUseCase by di.instance()

        SignUpViewModelFactory(
            signUpUseCase = signUpUseCase,
            saveTokenUseCase = saveTokenUseCase
        ).create(SignUpViewModel::class.java)
    }

    bindProvider {
        val signInUseCase: SignInUseCase by di.instance()
        val saveTokenUseCase: SaveTokenUseCase by di.instance()

        SignInViewModelFactory(
            signInUseCase = signInUseCase,
            saveTokenUseCase = saveTokenUseCase
        ).create(SignInViewModel::class.java)
    }

    bindProvider {
        ChatsViewModelFactory(getChatsUseCase = instance()).create(ChatsViewModel::class.java)
    }
}

val di: DI = DI {
    import(repositoryDi)
    import(useCasesDi)
    import(viewModelDi)
}