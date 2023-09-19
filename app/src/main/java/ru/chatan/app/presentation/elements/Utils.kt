package ru.chatan.app.presentation.elements

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed

fun Modifier.basicClickable(
    onClick: () -> Unit
): Modifier = composed {
    val interactionSource = remember { MutableInteractionSource() }
    return@composed this@basicClickable.clickable(
        interactionSource = interactionSource,
        indication = null
    ) { onClick() }
}