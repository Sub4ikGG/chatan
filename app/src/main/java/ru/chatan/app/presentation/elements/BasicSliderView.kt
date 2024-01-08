package ru.chatan.app.presentation.elements

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import ru.chatan.app.presentation.theme.ChatanTheme

@Composable
fun BasicSliderView() {
    val context = LocalContext.current
    var sliderPosition by remember { mutableStateOf(0f) }
    LaunchedEffect(key1 = sliderPosition) {
        Toast.makeText(context, sliderPosition.toInt().toString(), Toast.LENGTH_SHORT).show()
    }
    Column {
        Slider(
            value = sliderPosition,
            onValueChange = { sliderPosition = it },
            colors = SliderDefaults.colors(
                thumbColor = MaterialTheme.colorScheme.secondary,
                activeTrackColor = MaterialTheme.colorScheme.secondary,
                inactiveTrackColor = MaterialTheme.colorScheme.secondaryContainer,
            ),
            steps = 2,
            valueRange = 0f..4f
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun BasicSliderViewPreview() {
    ChatanTheme {
        BasicSliderView()
    }
}