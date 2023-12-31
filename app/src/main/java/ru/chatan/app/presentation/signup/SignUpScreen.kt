package ru.chatan.app.presentation.signup

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import cafe.adriel.voyager.androidx.AndroidScreen
import org.kodein.di.instance
import ru.chatan.app.di.di

class SignUpScreen: AndroidScreen() {

    @Composable
    override fun Content() {
        val signUpViewModel: SignUpViewModel by di.instance()
        val viewModel = viewModel { signUpViewModel }

        SignUpView(
            viewModel = viewModel
        )
    }

}