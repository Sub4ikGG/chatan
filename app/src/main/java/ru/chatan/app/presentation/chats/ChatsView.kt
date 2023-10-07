package ru.chatan.app.presentation.chats

import androidx.compose.foundation.background
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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cafe.adriel.voyager.navigator.LocalNavigator
import org.kodein.di.instance
import ru.chatan.app.di.di
import ru.chatan.app.presentation.chat.ChatScreen
import ru.chatan.app.presentation.elements.ChatBottomBar
import ru.chatan.app.presentation.elements.ChatsToolBarView

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatsView(
    viewModel: ChatsViewModel
) {
    val navigator = LocalNavigator.current
    val context = LocalContext.current
    val state by viewModel.state.collectAsStateWithLifecycle()

    Scaffold { contentPadding ->
        Box(
            modifier = Modifier
                .padding(top = contentPadding.calculateTopPadding())
                .background(Color.White)
        ) {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                ChatsToolBarView(
                    text = "Чаты"
                )

                LazyColumn(
                    modifier = Modifier
                        .padding(16.dp)
                        .weight(1f),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(state.chats) { chat ->
                        ChatItemView(chat = chat, click = {
                            navigator?.push(ChatScreen(chat = chat))
                        })
                    }
                }

                ChatBottomBar(
                    mailClick = {  },
                    addChatClick = {  },
                    profileClick = {  },
                    paddingBottom = contentPadding.calculateBottomPadding()
                )
            }
        }
    }
}

@Preview
@Composable
fun ChatsViewPreview() {
    val screenModel: ChatsViewModel by di.instance()

    ChatsView(viewModel = screenModel)
}