package ru.chatan.app.presentation.elements

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun ChatBottomBarItem(
    modifier: Modifier,
    imageVector: ImageVector,
    contentDescription: String = "Bottom bar item",
    click: () -> Unit = {}
) {
    Icon(
        modifier = modifier
            .height(32.dp)
            .clickable {
                click()
            },
        imageVector = imageVector,
        contentDescription = contentDescription,
        tint = Color.White
    )
}