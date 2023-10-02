package ru.chatan.app.presentation.elements

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ChatsToolBarView(
    text: String,
    backButtonVisible: Boolean = false,
    onBackPressed: () -> Unit = {}
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Column {
            Row(
                modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 16.dp),
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

            Spacer(modifier = Modifier
                .padding(top = 4.dp, start = 16.dp)
                .fillMaxWidth()
                .height(1.dp)
                .background(Color(0xFFF6F4F4))
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ChatsToolBarView() {
    BasicToolBarView(
        text = "Навигатор",
        backButtonVisible = true
    )
}