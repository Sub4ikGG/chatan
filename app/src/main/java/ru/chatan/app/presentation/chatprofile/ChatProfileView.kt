package ru.chatan.app.presentation.chatprofile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.LocalNavigator
import coil.compose.AsyncImage
import ru.chatan.app.domain.models.chat.Chat
import ru.chatan.app.domain.models.chat.ChatAvatar
import ru.chatan.app.presentation.elements.BasicToolBarView
import ru.chatan.app.presentation.theme.ChatanTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatProfileView(
    chat: Chat
) {
    val navigator = LocalNavigator.current

    Scaffold { contentPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0x80F6F4F4))
                .padding(top = contentPadding.calculateTopPadding()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            BasicToolBarView(
                text = chat.name,
                backButtonVisible = true,
                onBackPressed = {
                    navigator?.pop()
                })

            Box(
                modifier = Modifier
                    .width(IntrinsicSize.Min)
                    .padding(top = 16.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    AsyncImage(
                        model = chat.avatar.href,
                        modifier = Modifier
                            .size(128.dp)
                            .clip(CircleShape)
                            .background(Color.White),
                        contentScale = ContentScale.Crop,
                        contentDescription = "${chat.name} avatar"
                    )
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 16.dp)
                    .clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
                    .background(Color.White)
                    .padding(
                        top = 16.dp,
                        start = 16.dp,
                        end = 16.dp,
                        bottom = 16.dp + contentPadding.calculateBottomPadding()
                    )
            ) {

            }
        }
    }
}

@Preview
@Composable
fun ChatProfileViewPreview() {
    ChatanTheme {
        ChatProfileView(
            chat = Chat(
                id = 0,
                name = "CHATAN!",
                description = "",
                code = "",
                avatar = ChatAvatar("")
            )
        )
    }
}