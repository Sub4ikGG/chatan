package ru.chatan.app.di

import org.kodein.di.DI
import org.kodein.di.bindProvider
import org.kodein.di.bindSingleton
import org.kodein.di.instance
import ru.chatan.app.data.repository.AuthRepositoryImpl
import ru.chatan.app.data.repository.ChatsRepositoryImpl
import ru.chatan.app.data.repository.MessageRepositoryImpl
import ru.chatan.app.domain.repository.AuthRepository
import ru.chatan.app.domain.repository.ChatsRepository
import ru.chatan.app.domain.repository.MessageRepository
import ru.chatan.app.domain.usecases.ConnectChatUseCase
import ru.chatan.app.domain.usecases.GetChatsUseCase
import ru.chatan.app.domain.usecases.GetMessagesUseCase
import ru.chatan.app.domain.usecases.SaveTokenUseCase
import ru.chatan.app.domain.usecases.SendMessagesUseCase
import ru.chatan.app.domain.usecases.SignInAutoUseCase
import ru.chatan.app.domain.usecases.SignInUseCase
import ru.chatan.app.domain.usecases.SignUpUseCase
import ru.chatan.app.domain.usecases.StopMessagesUseCase
import ru.chatan.app.presentation.chat.ChatManager
import ru.chatan.app.presentation.chat.ChatViewModel
import ru.chatan.app.presentation.chat.ChatViewModelFactory
import ru.chatan.app.presentation.chats.ChatsViewModel
import ru.chatan.app.presentation.chats.ChatsViewModelFactory
import ru.chatan.app.presentation.connectchat.ConnectChatViewModel
import ru.chatan.app.presentation.connectchat.ConnectChatViewModelFactory
import ru.chatan.app.presentation.signin.SignInViewModel
import ru.chatan.app.presentation.signin.SignInViewModelFactory
import ru.chatan.app.presentation.signup.SignUpViewModel
import ru.chatan.app.presentation.signup.SignUpViewModelFactory
import ru.chatan.app.presentation.start.StartViewModel
import ru.chatan.app.presentation.start.StartViewModelFactory

private val featuresDi = DI.Module("featuresDi") {
    bindSingleton { ChatManager() }
}

private val repositoryDi = DI.Module("repositoryDi") {
    bindProvider<AuthRepository> { AuthRepositoryImpl() }
    bindProvider<ChatsRepository> { ChatsRepositoryImpl() }
    bindProvider<MessageRepository> { MessageRepositoryImpl() }
}

private val useCasesDi = DI.Module("useCasesDi") {
    bindProvider { SaveTokenUseCase() }

    bindProvider { SignUpUseCase() }
    bindProvider { SignInUseCase() }
    bindProvider { SignInAutoUseCase() }

    bindProvider { GetChatsUseCase() }
    bindProvider { ConnectChatUseCase() }
    bindProvider { GetMessagesUseCase() }
    bindProvider { SendMessagesUseCase() }
    bindProvider { StopMessagesUseCase() }
}

private val viewModelDi = DI.Module("viewModelDi") {
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

    bindProvider {
        ChatViewModelFactory(
            getMessagesUseCase = instance(),
            sendMessagesUseCase = instance(),
            stopMessagesUseCase = instance()
        ).create(ChatViewModel::class.java)
    }

    bindProvider {
        ConnectChatViewModelFactory(
            connectChatUseCase = instance()
        ).create(ConnectChatViewModel::class.java)
    }
}

val di: DI = DI {
    import(featuresDi)
    import(repositoryDi)
    import(useCasesDi)
    import(viewModelDi)
}