package ru.chatan.app.presentation.signin

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import cafe.adriel.voyager.androidx.AndroidScreen
import org.kodein.di.instance
import ru.chatan.app.di.di

class SignInScreen: AndroidScreen() {

    @Composable
    override fun Content() {
        val signInViewModel: SignInViewModel by di.instance()
        val viewModel = viewModel { signInViewModel }

        SignInView(
            viewModel = viewModel
        )
    }

}