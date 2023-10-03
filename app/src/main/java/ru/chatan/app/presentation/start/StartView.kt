package ru.chatan.app.presentation.start

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cafe.adriel.voyager.navigator.LocalNavigator
import org.kodein.di.instance
import ru.chatan.app.di.di
import ru.chatan.app.presentation.chats.ChatsScreen
import ru.chatan.app.presentation.theme.ChatanTheme
import ru.chatan.app.presentation.welcome.WelcomeScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StartView(viewModel: StartViewModel) {
    val navigator = LocalNavigator.current
    val context = LocalContext.current

    val state by viewModel.state.collectAsStateWithLifecycle()

    if (state.isSignInAutoSuccess == true) navigator?.push(ChatsScreen())
    else if (state.isSignInAutoSuccess == false) {
        if (state.error != null) Toast.makeText(context, state.error.orEmpty(), Toast.LENGTH_LONG).show()

        navigator?.push(WelcomeScreen())
    }

    Scaffold { contentPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding),
            contentAlignment = Alignment.Center
        ) {
            Text(
                modifier = Modifier
                    .background(color = Color.Red).padding(8.dp),
                text = "CHATAN!",
                fontWeight = FontWeight.Bold,
                color = Color.White,
                fontSize = 42.sp
            )
        }
    }
}

@Preview
@Composable
fun StartViewPreview() {
    ChatanTheme {
        val viewModel: StartViewModel by di.instance()
        StartView(viewModel = viewModel)
    }
}