package ru.chatan.app.presentation.start

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import cafe.adriel.voyager.androidx.AndroidScreen
import org.kodein.di.instance
import ru.chatan.app.di.di
import ru.efremovkirill.localstorage.LocalStorage

class StartScreen : AndroidScreen() {

    @Composable
    override fun Content() {
        val startViewModel: StartViewModel by di.instance()
        val viewModel = viewModel { startViewModel }

        val localStorage = LocalStorage.newInstance()
        val deviceId = localStorage.get("deviceId")
        val refreshToken = localStorage.get(LocalStorage.REFRESH_TOKEN)
        Log.d("StartScreen", "deviceId: $deviceId\nrefreshToken: $refreshToken")

        StartView(
            viewModel = viewModel
        )
    }

}