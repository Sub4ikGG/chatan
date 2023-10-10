package ru.chatan.app.presentation.connectchat

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import cafe.adriel.voyager.core.screen.Screen
import org.kodein.di.instance
import ru.chatan.app.di.di

class ConnectChatScreen : Screen {

    @Composable
    override fun Content() {
        val connectChatViewModel: ConnectChatViewModel by di.instance()
        val viewModel = viewModel { connectChatViewModel }

        ConnectChatView(viewModel = viewModel)
    }

}