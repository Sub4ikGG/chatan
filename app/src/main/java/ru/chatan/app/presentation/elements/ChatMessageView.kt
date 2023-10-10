package ru.chatan.app.presentation.elements

import androidx.compose.foundation.Image
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.chatan.app.R
import ru.chatan.app.domain.models.chat.ChatUser
import ru.chatan.app.domain.models.message.ChatMessage
import ru.chatan.app.domain.models.message.MessageType
import ru.chatan.app.getCalendarTime
import ru.chatan.app.presentation.theme.ChatanTheme

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
            if (!self && (messageType == MessageType.SINGLE || messageType == MessageType.END))
                Box(
                    modifier = Modifier.fillMaxHeight().padding(start = 4.dp, end = 4.dp),
                    contentAlignment = Alignment.BottomCenter
                ) {
                    Image(
                        modifier = Modifier
                            .size(38.dp)
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop,
                        painter = painterResource(id = R.drawable.sample_avatar),
                        contentDescription = "Sample avatar"
                    )
                }
            else if (!self || chatMessage.user == null)
                Box(
                    modifier = Modifier.fillMaxHeight().padding(start = 4.dp, end = 4.dp),
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
                            color = Color.Black,
                            style = MaterialTheme.typography.titleSmall
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
                                .background(Color(0xFFF6F4F4))
                                .padding(10.dp)
                                .weight(1f, fill = false),
                            text = chatMessage.body,
                            color = Color.Black,
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
                user = ChatUser(id = 0, name = "Alan Walker"),
                body = "Привет, макакереререререререререререререререререпавпвапвапвапв",
                date = System.currentTimeMillis()
            ),
            messageType = MessageType.SINGLE
        )
    }
}