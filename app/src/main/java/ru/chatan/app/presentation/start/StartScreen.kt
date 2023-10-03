package ru.chatan.app.presentation.start

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import cafe.adriel.voyager.androidx.AndroidScreen
import org.kodein.di.instance
import ru.chatan.app.di.di

class StartScreen : AndroidScreen() {

    @Composable
    override fun Content() {
        val startViewModel: StartViewModel by di.instance()
        val viewModel = viewModel { startViewModel }

        StartView(
            viewModel = viewModel
        )
    }

}