package ru.chatan.app.presentation.createchat

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cafe.adriel.voyager.navigator.LocalNavigator
import org.kodein.di.instance
import ru.chatan.app.R
import ru.chatan.app.di.di
import ru.chatan.app.domain.models.chat.CreateChat
import ru.chatan.app.presentation.chat.ChatScreen
import ru.chatan.app.presentation.elements.BasicButton
import ru.chatan.app.presentation.elements.BasicTextFieldView
import ru.chatan.app.presentation.elements.BasicToolBarView
import ru.chatan.app.presentation.theme.ChatanTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateChatView(
    viewModel: CreateChatViewModel
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    val context = LocalContext.current
    val navigator = LocalNavigator.current

    var code by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var size by remember { mutableStateOf("") }
    val buttonText = state.error.orEmpty().ifEmpty { "Создать" }

    LaunchedEffect(key1 = state) {
        state.chat?.also { chat ->
            navigator?.replace(ChatScreen(chat = chat))
        }
    }

    Scaffold { contentPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(contentPadding)
                .imePadding(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            BasicToolBarView(text = "Создать чат", backButtonVisible = true, onBackPressed = {
                navigator?.pop()
            })

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        modifier = Modifier
                            .size(128.dp)
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop,
                        painter = painterResource(id = R.drawable.sample_avatar),
                        contentDescription = "Your future avatar"
                    )

                    Text(
                        text = "Добавить аватар чата",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Gray
                    )
                }
            }

            Spacer(
                modifier = Modifier
                    .padding(vertical = 16.dp)
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(Color(0xFFF6F4F4))
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp)
            ) {
                BasicTextFieldView(
                    modifier = Modifier.fillMaxWidth(),
                    text = code,
                    hint = "Код чата",
                    onValueChange = {
                        code = it
                    },
                    onClear = {}
                )

                BasicTextFieldView(
                    modifier = Modifier.fillMaxWidth(),
                    text = name,
                    hint = "Название чата",
                    onValueChange = {
                        name = it
                    },
                    onClear = {
                        name = ""
                    }
                )

                BasicTextFieldView(
                    modifier = Modifier.fillMaxWidth(),
                    text = description,
                    hint = "Описание чата",
                    onValueChange = {
                        description = it
                    },
                    onClear = {
                        description = ""
                    },
                    singleLine = false
                )

                BasicTextFieldView(
                    modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
                    text = size,
                    hint = "Количество участников",
                    onValueChange = {
                        val value = it.replace(" чел.", "").toIntOrNull()

                        value?.also { count ->
                            if (count < 2) {
                                size = "2 чел."
                                return@BasicTextFieldView
                            }
                        }

                        size = if (value != null) {
                            "$value чел."
                        } else ""
                    },
                    onClear = {
                        size = ""
                    }
                )

                Spacer(modifier = Modifier.weight(1f))

                BasicButton(
                    modifier = Modifier.fillMaxWidth(),
                    text = buttonText,
                    onClick = {
                        viewModel.send(event = CreateChatEvent.CreateChat(
                            CreateChat(
                                code = code,
                                name = name,
                                description = description,
                                userLimit = size.replace(" чел.", "").toIntOrNull() ?: 0
                            )
                        ))
                    },
                    loading = state.loading,
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun CreateChatViewPreview() {
    ChatanTheme {
        val viewModel: CreateChatViewModel by di.instance()

        CreateChatView(viewModel = viewModel)
    }
}