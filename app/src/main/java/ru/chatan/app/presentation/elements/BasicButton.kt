package ru.chatan.app.presentation.elements

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.chatan.app.presentation.theme.ChatanTheme
import ru.chatan.app.presentation.theme.HardBlue

@Composable
fun BasicButton(
    modifier: Modifier = Modifier,
    text: String = "CHATAN",
    enabled: Boolean = true,
    loading: Boolean = false,
    onClick: () -> Unit = {}
) {
    Button(
        modifier = modifier.defaultMinSize(minHeight = 48.dp),
        shape = RoundedCornerShape(10.dp),
        enabled = enabled,
        colors = ButtonDefaults.buttonColors(containerColor = HardBlue),
        onClick = {
            if (!loading)
                onClick()
        }
    ) {
        AnimatedVisibility(visible = loading) {
            CircularProgressIndicator(
                modifier = Modifier.then(Modifier.size(32.dp)),
                color = Color.White
            )
        }

        AnimatedVisibility(visible = !loading) {
            Text(text = text, style = TextStyle(fontSize = 16.sp), fontWeight = FontWeight.Bold)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BasicBlackButtonPreview() {
    ChatanTheme {
        BasicButton(
            modifier = Modifier.width(256.dp).height(48.dp),
            loading = true,
            text = "Приступить"
        )
    }
}