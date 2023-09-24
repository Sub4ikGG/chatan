package ru.chatan.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.view.WindowCompat
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import ru.chatan.app.presentation.theme.ChatanTheme
import ru.chatan.app.presentation.welcome.WelcomeScreen
import ru.chatan.app.presentation.welcome.WelcomeView

@OptIn(ExperimentalAnimationApi::class)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            ChatanTheme {
                val systemUiController = rememberSystemUiController()

                SideEffect {
                    // Update all of the system bar colors to be transparent, and use
                    // dark icons if we're in light theme
                    systemUiController.setSystemBarsColor(
                        color = Color.Transparent,
                        darkIcons = true
                    )
                }

                Navigator(WelcomeScreen()) {
                    SlideTransition(it)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainPreview() {
    ChatanTheme {
        WelcomeView()
    }
}