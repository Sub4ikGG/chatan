package ru.chatan.app.presentation.connectchat

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cafe.adriel.voyager.navigator.LocalNavigator
import org.kodein.di.instance
import ru.chatan.app.di.*
import ru.chatan.app.presentation.chat.ChatScreen
import ru.chatan.app.presentation.elements.BasicButton
import ru.chatan.app.presentation.elements.BasicToolBarView
import ru.chatan.app.presentation.theme.ChatanTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConnectChatView(
    viewModel: ConnectChatViewModel
) {
    val navigator = LocalNavigator.current
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = state) {
        state.chat?.also {
            navigator?.replace(ChatScreen(chat = it))
        }
    }

    var code by remember { mutableStateOf("") }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) { contentPadding ->
        Column(
            modifier = Modifier
                .background(Color.White)
                .padding(contentPadding)
        ) {
            BasicToolBarView(
                text = "Код приглашения",
                backButtonVisible = true,
                onBackPressed = {
                    navigator?.pop()
                }
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                Spacer(modifier = Modifier.weight(1f))

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .width(IntrinsicSize.Max),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    TextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = code,
                        onValueChange = {
                            code = it
                        },
                        textStyle = MaterialTheme
                            .typography
                            .displaySmall
                            .copy(
                                textAlign = TextAlign.Center,
                                fontWeight = FontWeight.Bold
                            ),
                        placeholder = {
                            Text(
                                modifier = Modifier.fillMaxWidth(),
                                text = "#CHATAN",
                                color = Color.Gray,
                                style = MaterialTheme
                                    .typography
                                    .displaySmall
                                    .copy(
                                        textAlign = TextAlign.Center,
                                        fontWeight = FontWeight.Bold
                                    ),
                                fontWeight = FontWeight.Bold
                            )
                        },
                        shape = RoundedCornerShape(0.dp),
                        colors = TextFieldDefaults.textFieldColors(
                            containerColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent
                        ),
                        singleLine = true
                    )
                }

                Spacer(modifier = Modifier.weight(1f))

                BasicButton(
                    modifier = Modifier.fillMaxWidth(),
                    text = state.error ?: "Присоединиться",
                    onClick = {
                        if (code.isNotBlank())
                            viewModel.send(event = ConnectChatEvent.Connect(code = code))
                    },
                    loading = state.isLoading
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ConnectChatViewPreview() {
    ChatanTheme {
        val viewModel: ConnectChatViewModel by di.instance()

        ConnectChatView(
            viewModel = viewModel
        )
    }
}