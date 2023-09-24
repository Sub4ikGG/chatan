package ru.chatan.app.presentation.signin

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import org.kodein.di.instance
import ru.chatan.app.di.di

class SignInScreen: Screen {

    @Composable
    override fun Content() {
        val signInScreenModel: SignInScreenModel by di.instance()
        val screenModel = rememberScreenModel { signInScreenModel }

        SignInView(
            screenModel = screenModel
        )
    }

}