package ru.chatan.app.presentation.signup

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import org.kodein.di.instance
import ru.chatan.app.di.di

class SignUpScreen: Screen {

    @Composable
    override fun Content() {
        val signUpScreenModel: SignUpScreenModel by di.instance()
        val screenModel = rememberScreenModel { signUpScreenModel }

        SignUpView(
            screenModel = screenModel
        )
    }

}