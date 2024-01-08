package ru.chatan.app.presentation.elements

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.chatan.app.presentation.theme.ChatanTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BasicTextFieldView(
    modifier: Modifier,
    text: String,
    hint: String,
    keyboardOptions: KeyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
    onValueChange: (String) -> Unit,
    onClear: () -> Unit,
    mask: String = "",
    singleLine: Boolean = true
) {
    val visualTransformation =
        if (keyboardOptions.keyboardType == KeyboardType.Password)
            PasswordVisualTransformation()
        else if (mask.isNotBlank()) MaskVisualTransformation(mask)
        else VisualTransformation.None

    Box {
        OutlinedTextField(
            modifier = modifier,
            value = text,
            visualTransformation = visualTransformation,
            keyboardOptions = keyboardOptions,
            shape = RoundedCornerShape(10.dp),
            trailingIcon = {
                AnimatedVisibility (text.isNotBlank()) {
                    Icon(
                        modifier = Modifier.basicClickable {
                            onClear()
                        },
                        imageVector = Icons.Default.Clear,
                        contentDescription = "Clear text field"
                    )
                }
            },
            singleLine = singleLine,
            onValueChange = onValueChange,
            label = { Text(text = hint) }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun BasicTextFieldPreview() {
    ChatanTheme {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            var text by remember { mutableStateOf("Hello, CHATAN! how to you fdsf f orma reomfn dohe odfjrel dleoqfnbkd") }
            BasicTextFieldView(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                text = text,
                hint = "Hint",
                onValueChange = {},
                onClear = { text = "" }
            )
        }
    }
}