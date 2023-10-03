package ru.chatan.app.presentation.chat

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.LocalNavigator
import ru.chatan.app.domain.models.chat.Chat
import ru.chatan.app.presentation.chats.ChatItemView
import ru.chatan.app.presentation.elements.ChatToolBarView
import ru.chatan.app.presentation.sampleMessages
import ru.chatan.app.presentation.theme.ChatanTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatView(
    chat: Chat
) {
    val context = LocalContext.current
    val navigator = LocalNavigator.current

    Scaffold { contentPadding ->
        Column(
            modifier = Modifier
                .padding(contentPadding)
                .fillMaxSize()
        ) {
            ChatToolBarView(
                text = chat.name,
                onBackPressed = {
                    navigator?.pop()
                }
            )

            Box(
                modifier = Modifier.weight(1f),
                contentAlignment = Alignment.BottomCenter
            ) {
                LazyColumn(
                    modifier = Modifier
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(sampleMessages) { chat ->
                        ChatItemView(chat = chat, click = {
                            Toast.makeText(context, chat.name, Toast.LENGTH_SHORT).show()
                        })
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun ChatViewPreview() {
    ChatanTheme {
        val chat = Chat(
            chatId = 0,
            name = "CHATAN!"
        )

        ChatView(chat = chat)
    }
}