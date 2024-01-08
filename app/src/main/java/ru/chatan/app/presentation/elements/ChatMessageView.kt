package ru.chatan.app.presentation.elements

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import ru.chatan.app.domain.models.message.ChatMessage
import ru.chatan.app.domain.models.message.MessageType
import ru.chatan.app.domain.models.user.User
import ru.chatan.app.domain.models.user.UserAvatar
import ru.chatan.app.getCalendarTime
import ru.chatan.app.presentation.theme.ChatanTheme
import ru.chatan.app.presentation.theme.Gray
import ru.chatan.app.presentation.theme.HardBlue

@Composable
fun ChatMessageView(
    modifier: Modifier = Modifier,
    self: Boolean,
    chatMessage: ChatMessage,
    messageType: MessageType
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Max)
    ) {
        Row {
            if (!self && (messageType == MessageType.SINGLE || messageType == MessageType.END) && chatMessage.user != null)
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .padding(start = 4.dp, end = 4.dp),
                    contentAlignment = Alignment.BottomCenter
                ) {
                    AsyncImage(
                        model = chatMessage.user.avatar.href,
                        modifier = Modifier
                            .size(38.dp)
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop,
                        contentDescription = "${chatMessage.user.name} avatar"
                    )
                }
            else if (chatMessage.user == null) {
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .padding(start = 4.dp, end = 4.dp),
                    contentAlignment = Alignment.BottomCenter
                ) {
                    Icon(
                        modifier = Modifier
                            .size(38.dp)
                            .clip(CircleShape),
                        imageVector = Icons.Default.KeyboardArrowRight,
                        contentDescription = "System avatar"
                    )
                }
            }
            else if (!self)
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .padding(start = 4.dp, end = 4.dp),
                    contentAlignment = Alignment.BottomCenter
                ) {
                    Spacer(modifier = Modifier.size(38.dp))
                }

            Column {
                if (!self && (messageType == MessageType.SINGLE || messageType == MessageType.TOP))
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        Text(
                            modifier = Modifier.padding(start = 4.dp, end = 4.dp, bottom = 4.dp),
                            text = chatMessage.user?.name ?: "System",
                            color = Color.LightGray,
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Light
                        )
                    }

                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = if (self) Alignment.CenterEnd else Alignment.CenterStart
                ) {
                    Row(
                        modifier = Modifier.height(IntrinsicSize.Max)
                    ) {
                        if (self)
                            Box(
                                contentAlignment = Alignment.BottomCenter,
                                modifier = Modifier
                                    .fillMaxHeight()
                                    .padding(start = 64.dp, end = 4.dp)
                            ) {
                                Text(
                                    text = chatMessage.date.getCalendarTime(),
                                    color = Color.Gray,
                                    style = MaterialTheme.typography.labelMedium,
                                    fontWeight = FontWeight.Light
                                )
                            }

                        Text(
                            modifier = Modifier
                                .clip(RoundedCornerShape(size = 10.dp))
                                .background(if (self) HardBlue else Gray)
                                .padding(horizontal = 16.dp, vertical = 12.dp)
                                .weight(1f, fill = false),
                            text = chatMessage.body,
                            color = if (self) Color.White else Color.Black,
                            style = MaterialTheme.typography.bodyLarge
                        )

                        if (!self)
                            Box(
                                contentAlignment = Alignment.BottomCenter,
                                modifier = Modifier
                                    .fillMaxHeight()
                                    .padding(start = 4.dp, end = 64.dp)
                            ) {
                                Text(
                                    text = chatMessage.date.getCalendarTime(),
                                    color = Color.Gray,
                                    style = MaterialTheme.typography.labelMedium,
                                    fontWeight = FontWeight.Light
                                )
                            }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ChatMessageViewPreview() {
    ChatanTheme {
        ChatMessageView(
            self = false,
            chatMessage = ChatMessage(
                id = 0,
                user = User(id = 0, name = "Alan Walker", avatar = UserAvatar("")),
                body = "Привет, макакереререререререререререререререререпавпвапвапвапв",
                date = System.currentTimeMillis()
            ),
            messageType = MessageType.SINGLE
        )
    }
}