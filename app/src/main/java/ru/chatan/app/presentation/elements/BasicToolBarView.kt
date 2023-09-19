package ru.chatan.app.presentation.elements

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun BasicToolBarView(
    text: String,
    backButtonVisible: Boolean = false,
    onBackPressed: () -> Unit = {}
) {
    val interactionSource = remember { MutableInteractionSource() }

    Box(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (backButtonVisible) {
                Icon(
                    modifier = Modifier.basicClickable {
                        onBackPressed()
                    },
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back button"
                )
                Spacer(modifier = Modifier.width(16.dp))
            }

            Text(
                text = text,
                style = TextStyle(fontSize = 32.sp),
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BasicToolBarPreview() {
    BasicToolBarView(
        text = "Навигатор",
        backButtonVisible = true
    )
}