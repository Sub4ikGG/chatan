package ru.chatan.app.presentation.chats

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import cafe.adriel.voyager.androidx.AndroidScreen
import cafe.adriel.voyager.core.lifecycle.LifecycleEffect
import org.kodein.di.instance
import ru.chatan.app.di.di

class ChatsScreen : AndroidScreen() {

    @Composable
    override fun Content() {
        val chatsViewModel: ChatsViewModel by di.instance()
        val viewModel = viewModel { chatsViewModel }

        LifecycleEffect(
            onStarted = {
                viewModel.send(event = ChatsEvent.GetChats)
            }
        )

        ChatsView(
            viewModel = viewModel
        )
    }

}