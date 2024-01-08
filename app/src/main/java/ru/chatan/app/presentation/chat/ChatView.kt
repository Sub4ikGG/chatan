package ru.chatan.app.presentation.chat

import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
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
import ru.chatan.app.domain.models.chat.ChatAvatar
import ru.chatan.app.domain.models.message.ChatMessage
import ru.chatan.app.domain.models.message.MessageType
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
                .fillMaxSize()
                .background(Color.White)
                .padding(contentPadding)
                .imePadding()
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
                    itemsIndexed(
                        items = state.messages,
                        key = { _, item ->
                            item.id
                        }
                    ) { index, chatMessage ->
                        ChatMessageView(
                            modifier = Modifier.animateItemPlacement(),
                            self = chatMessage.user?.name == userName,
                            chatMessage = chatMessage,
                            messageType = getMessageType(index = index, messages = state.messages)
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

/**
 * Remember that messages going from bottom of screen
 */
private fun getMessageType(index: Int, messages: List<ChatMessage>): MessageType {
    val nextMessageUserName = messages.getOrNull(index - 1)?.user?.name
    val currentMessageUserName = messages[index].user?.name
    val previousMessageUserName = messages.getOrNull(index + 1)?.user?.name

    if (currentMessageUserName == previousMessageUserName && currentMessageUserName == nextMessageUserName)
        return MessageType.MIDDLE

    if (currentMessageUserName != previousMessageUserName && currentMessageUserName == nextMessageUserName)
        return MessageType.TOP

    if (currentMessageUserName == previousMessageUserName)
        return MessageType.END

    return MessageType.SINGLE
}

@Preview
@Composable
fun ChatViewPreview() {
    ChatanTheme {
        val viewModel: ChatViewModel by di.instance()
        val chat = Chat(
            id = 0, name = "CHATAN!",
            description = "",
            code = "",
            avatar = ChatAvatar("")
        )

        ChatView(chat = chat, viewModel = viewModel, userName = "Alan Walker")
    }
}