package ru.chatan.app.presentation.profile

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import cafe.adriel.voyager.androidx.AndroidScreen
import org.kodein.di.instance
import ru.chatan.app.di.di

class ProfileScreen : AndroidScreen() {

    private val profileViewModel: ProfileViewModel by di.instance()

    @Composable
    override fun Content() {
        val viewModel = viewModel { profileViewModel }

        ProfileView(
            viewModel = viewModel
        )
    }

}