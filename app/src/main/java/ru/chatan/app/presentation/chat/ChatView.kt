package ru.chatan.app.presentation.chat

import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cafe.adriel.voyager.navigator.LocalNavigator
import kotlinx.coroutines.launch
import org.kodein.di.instance
import ru.chatan.app.di.di
import ru.chatan.app.domain.models.chat.Chat
import ru.chatan.app.domain.models.message.SendMessage
import ru.chatan.app.presentation.elements.ChatMessageView
import ru.chatan.app.presentation.elements.ChatToolBarView
import ru.chatan.app.presentation.elements.basicClickable
import ru.chatan.app.presentation.theme.ChatanTheme

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun ChatView(
    userName: String,
    chat: Chat,
    viewModel: ChatViewModel
) {
    val context = LocalContext.current
    val navigator = LocalNavigator.current

    val coroutineScope = rememberCoroutineScope()
    val state by viewModel.state.collectAsStateWithLifecycle()

    var body by remember { mutableStateOf("") }
    val lazyListState = rememberLazyListState()

    if (state.error != null)
        Toast.makeText(context, state.error.orEmpty(), Toast.LENGTH_SHORT).show()

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
                        .padding(start = 8.dp, end = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    state = lazyListState,
                    reverseLayout = true
                ) {
                    items(
                        items = state.messages,
                        key = {
                            it.id
                        }
                    ) { chatMessage ->
                        ChatMessageView(
                            modifier = Modifier.animateItemPlacement(),
                            self = chatMessage.user?.name == userName,
                            chatMessage = chatMessage
                        )
                    }

                    coroutineScope.launch {
                        if (lazyListState.firstVisibleItemIndex < 2)
                            lazyListState.scrollToItem(0)
                    }
                }
            }

            Row(
                modifier = Modifier.padding(end = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextField(
                    modifier = Modifier.weight(1f),
                    value = body,
                    onValueChange = {
                        body = it
                    },
                    placeholder = {
                        Text(text = "Сообщение", color = Color.Gray)
                    },
                    shape = RoundedCornerShape(0.dp),
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    )
                )

                Icon(
                    modifier = Modifier.basicClickable {
                        coroutineScope.launch {
                            val message = SendMessage(body = body)
                            viewModel.send(ChatEvent.SendMessage(message = message)).join()
                            body = ""

                            lazyListState.scrollToItem(0)
                        }
                    },
                    imageVector = Icons.Default.Send,
                    contentDescription = "Back button",
                )
            }
        }
    }
}

@Preview
@Composable
fun ChatViewPreview() {
    ChatanTheme {
        val viewModel: ChatViewModel by di.instance()
        val chat = Chat(chatId = 0, name = "CHATAN!")

        ChatView(chat = chat, viewModel = viewModel, userName = "Alan Walker")
    }
}