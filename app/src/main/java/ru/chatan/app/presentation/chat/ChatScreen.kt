package ru.chatan.app.presentation.chat

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import cafe.adriel.voyager.androidx.AndroidScreen
import cafe.adriel.voyager.core.lifecycle.LifecycleEffect
import org.kodein.di.instance
import ru.chatan.app.di.di
import ru.chatan.app.domain.models.chat.Chat
import ru.chatan.app.presentation.User

class ChatScreen(
    val chat: Chat
): AndroidScreen() {

    @Composable
    override fun Content() {
        val chatViewModel: ChatViewModel by di.instance()
        val viewModel = viewModel { chatViewModel }

        LifecycleEffect(
            onStarted = {
                viewModel.send(ChatEvent.GetMessages(chatId = chat.id))
            }
        )

        ChatView(
            chat = chat,
            viewModel = viewModel,
            userName = User.user?.name.orEmpty(),
        )
    }

}