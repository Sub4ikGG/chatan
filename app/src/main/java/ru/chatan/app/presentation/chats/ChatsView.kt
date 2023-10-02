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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.kodein.di.instance
import ru.chatan.app.di.di
import ru.chatan.app.domain.models.messages.UserMessage
import ru.chatan.app.presentation.elements.ChatsToolBarView

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatsView(
    screenModel: ChatsViewModel
) {
    val state = screenModel.state.collectAsStateWithLifecycle()

    val sampleMessages = listOf(
        UserMessage(
            id = 0,
            name = "Alan Walker",
            message = "Hello world!",
            date = "now"
        ),
        UserMessage(
            id = 0,
            name = "Alan Walker",
            message = "Hello world!",
            date = "now"
        ),
        UserMessage(
            id = 0,
            name = "Alan Walker",
            message = "Hello world!",
            date = "now"
        ),
        UserMessage(
            id = 0,
            name = "Alan Walker",
            message = "Hello world!",
            date = "now"
        )
    )

    Scaffold { contentPadding ->
        Box(
            modifier = Modifier
                .padding(contentPadding)
                .background(Color.White)
        ) {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                ChatsToolBarView(text = "Чаты")

                LazyColumn(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(sampleMessages) { userMessage ->
                        MessageView(userMessage = userMessage)
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun ChatsViewPreview() {
    val screenModel: ChatsViewModel by di.instance()

    ChatsView(screenModel = screenModel)
}