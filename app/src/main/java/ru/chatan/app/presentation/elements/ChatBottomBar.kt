package ru.chatan.app.presentation.elements

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import ru.chatan.app.presentation.theme.ChatanTheme
import ru.chatan.app.presentation.theme.HardBlue

@Composable
fun ChatBottomBar(
    modifier: Modifier = Modifier,
    connectToTheChat: () -> Unit,
    createChat: () -> Unit,
    profileClick: () -> Unit,
    settingsClick: () -> Unit,
    paddingBottom: Dp
) {
    var dropdown by remember { mutableStateOf(false) }

    Column {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentSize(align = Alignment.TopCenter),
            contentAlignment = Alignment.TopCenter
        ) {
            DropdownMenu(
                expanded = dropdown,
                onDismissRequest = {
                    dropdown = false
                }
            ) {
                DropdownMenuItem(
                    text = {
                        Text(text = "Вступить в чат", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Normal)
                    },
                    onClick = {
                        dropdown = false
                        connectToTheChat()
                    },
                )

                DropdownMenuItem(
                    text = {
                        Text(text = "Создать чат", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Normal)
                    },
                    onClick = {
                        dropdown = false
                        createChat()
                    }
                )
            }
        }

        Box(
            modifier = modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp))
                .background(HardBlue)
                .padding(bottom = paddingBottom)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            ) {
                ChatBottomBarItem(
                    modifier = Modifier.weight(1f),
                    text = "Профиль",
                    imageVector = Icons.Default.Person,
                    click = profileClick
                )

                ChatBottomBarItem(
                    modifier = Modifier.weight(1f),
                    text = "Чат",
                    imageVector = Icons.Default.Add,
                    click = {
                        dropdown = true
                    }
                )

                ChatBottomBarItem(
                    modifier = Modifier.weight(1f),
                    text = "Настройки",
                    imageVector = Icons.Default.Settings,
                    click = settingsClick
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ChatBottomBarPreview() {
    ChatanTheme {
        ChatBottomBar(
            settingsClick = {},
            connectToTheChat = {},
            createChat = {},
            profileClick = {},
            paddingBottom = 0.dp
        )
    }
}