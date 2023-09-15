package ru.chatan.app.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.chatan.app.ui.theme.ChatanTheme

@Composable
fun BlackButton(modifier: Modifier = Modifier, text: String = "CHATAN", onClick: () -> Unit = {}) {
    Button(
        modifier = modifier,
        shape = RoundedCornerShape(10.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color.Black),
        onClick = onClick
    ) {
        Text(text = text, style = TextStyle(fontSize = 16.sp), fontWeight = FontWeight.Bold)
    }
}

@Preview(showBackground = true)
@Composable
fun BlackButtonPreview() {
    ChatanTheme {
        BlackButton(modifier = Modifier.width(256.dp).height(48.dp), text = "Приступить")
    }
}