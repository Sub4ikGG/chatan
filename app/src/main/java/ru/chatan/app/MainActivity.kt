package ru.chatan.app

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.view.WindowCompat
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition
import ru.chatan.app.presentation.chats.ChatsScreen
import ru.chatan.app.presentation.start.StartScreen
import ru.chatan.app.presentation.theme.ChatanTheme
import ru.chatan.app.presentation.welcome.WelcomeScreen
import ru.chatan.app.presentation.welcome.WelcomeView

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalAnimationApi::class)
class MainActivity : ComponentActivity() {

    private var networkManager: NetworkManager? = NetworkManager()

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)
        networkManager?.connect(activity = this@MainActivity)

        setContent {
            val snackbarHostState = remember { SnackbarHostState() }
            val network by remember { mutableStateOf(networkManager?.getNetworkState()) }

            CheckNetworkState(snackbarHostState, network)

            ChatanTheme {
                Scaffold(
                    snackbarHost = {
                        SnackbarHost(hostState = snackbarHostState)
                    }
                ) {
                    Navigator(
                        screen = StartScreen(),
                        onBackPressed = { currentScreen ->
                            when (currentScreen) {
                                is WelcomeScreen, is ChatsScreen -> {
                                    finish()
                                    false
                                }

                                else -> true
                            }
                        }
                    ) {
                        SlideTransition(it)
                    }
                }
            }
        }
    }

    @Composable
    private fun CheckNetworkState(
        snackbarHostState: SnackbarHostState,
        network: Boolean?
    ) {
        LaunchedEffect(key1 = network) {
            if (network == false)
                snackbarHostState.showSnackbar(
                    message = getString(R.string.network_unavailable),
                    duration = SnackbarDuration.Indefinite,
                    withDismissAction = true
                )
            else snackbarHostState.currentSnackbarData?.dismiss()
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        networkManager?.stop()
        networkManager = null
    }
}

@Preview(showBackground = true)
@Composable
fun MainPreview() {
    ChatanTheme {
        WelcomeView()
    }
}