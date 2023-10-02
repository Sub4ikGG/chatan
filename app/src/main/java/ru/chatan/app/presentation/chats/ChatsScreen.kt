package ru.chatan.app.presentation.chats

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import cafe.adriel.voyager.core.screen.Screen

class ChatsScreen : Screen {

    @Composable
    override fun Content() {
        val screenModel = viewModel<ChatsViewModel>()

        ChatsView(
            screenModel = screenModel
        )
    }

}