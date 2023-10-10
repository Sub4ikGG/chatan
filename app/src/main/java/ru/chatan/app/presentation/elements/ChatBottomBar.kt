package ru.chatan.app.presentation.elements

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import ru.chatan.app.presentation.theme.ChatanTheme

@Composable
fun ChatBottomBar(
    modifier: Modifier = Modifier,
    mailClick: () -> Unit,
    addChatClick: () -> Unit,
    profileClick: () -> Unit,
    paddingBottom: Dp
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp))
            .background(MaterialTheme.colorScheme.secondary)
            .padding(bottom = paddingBottom)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {
            ChatBottomBarItem(
                modifier = Modifier.weight(1f),
                text = "Сообщения",
                imageVector = Icons.Default.MailOutline,
                click = mailClick
            )

            ChatBottomBarItem(
                modifier = Modifier.weight(1f),
                text = "Новое",
                imageVector = Icons.Default.Add,
                click = addChatClick
            )

            ChatBottomBarItem(
                modifier = Modifier.weight(1f),
                text = "Профиль",
                imageVector = Icons.Default.Person,
                click = profileClick
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ChatBottomBarPreview() {
    ChatanTheme {
        ChatBottomBar(
            mailClick = {},
            addChatClick = {},
            profileClick = {},
            paddingBottom = 0.dp
        )
    }
}