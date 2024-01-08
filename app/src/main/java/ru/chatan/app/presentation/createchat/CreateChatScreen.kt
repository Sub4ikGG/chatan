package ru.chatan.app.presentation.createchat

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import cafe.adriel.voyager.core.screen.Screen
import org.kodein.di.instance
import ru.chatan.app.di.di

class CreateChatScreen : Screen {

    @Composable
    override fun Content() {
        val createChatViewModel: CreateChatViewModel by di.instance()
        val viewModel = viewModel { createChatViewModel }

        CreateChatView(
            viewModel = viewModel
        )
    }

}