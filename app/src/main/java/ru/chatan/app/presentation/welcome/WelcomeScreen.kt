package ru.chatan.app.presentation.welcome

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen

class WelcomeScreen: Screen {
    @Composable
    override fun Content() {
        WelcomeView()
    }
}