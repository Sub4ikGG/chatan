package ru.chatan.app.presentation.elements

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.chatan.app.presentation.theme.ChatanTheme

@Composable
fun ChatBottomBarItem(
    modifier: Modifier,
    imageVector: ImageVector,
    text: String,
    contentDescription: String = "Bottom bar item",
    click: () -> Unit = {}
) {
    Column(
        modifier = modifier
            .basicClickable {
                click()
            },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            modifier = Modifier.height(28.dp),
            imageVector = imageVector,
            contentDescription = contentDescription,
            tint = Color.White
        )

        Text(
            text = text,
            style = MaterialTheme.typography.labelMedium,
            color = Color.White,
            fontWeight = FontWeight.Medium
        )
    }
}

@Preview
@Composable
fun ChatBottomBarItemPreview() {
    ChatanTheme {
        ChatBottomBarItem(
            modifier = Modifier,
            imageVector = Icons.Default.MailOutline,
            text = "Сообщения",
            click = {}
        )
    }
}