package ru.chatan.app.presentation.signin

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cafe.adriel.voyager.navigator.LocalNavigator
import org.kodein.di.instance
import ru.chatan.app.di.di
import ru.chatan.app.presentation.chats.ChatsScreen
import ru.chatan.app.presentation.elements.BasicBlackButton
import ru.chatan.app.presentation.elements.BasicTextFieldView
import ru.chatan.app.presentation.elements.BasicToolBarView

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignInView(
    viewModel: SignInViewModel
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    val context = LocalContext.current
    val navigator = LocalNavigator.current

    var login by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    if (state.isSignInSuccess == false && state.error != null)
        Toast.makeText(context, state.error.orEmpty(), Toast.LENGTH_SHORT).show()
    else if (state.isSignInSuccess == true)
        navigator?.push(ChatsScreen())

    Scaffold { contentPadding ->

        Box(
            modifier = Modifier
                .background(Color.White)
                .padding(contentPadding)
                .fillMaxSize()
        ) {
            Column {
                BasicToolBarView(
                    text = "Авторизация",
                    backButtonVisible = true,
                    onBackPressed = {
                        navigator?.pop()
                    }
                )

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column {
                        Spacer(modifier = Modifier.weight(1f))

                        BasicTextFieldView(
                            modifier = Modifier.fillMaxWidth(),
                            text = login,
                            hint = "Логин",
                            onValueChange = {
                                login = it
                            },
                            onClear = {
                                login = ""
                            }
                        )

                        BasicTextFieldView(
                            modifier = Modifier.fillMaxWidth(),
                            text = password,
                            hint = "Пароль",
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                            onValueChange = {
                                password = it
                            },
                            onClear = {
                                password = ""
                            }
                        )

                        Spacer(modifier = Modifier.weight(1f))

                        BasicBlackButton(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 16.dp),
                            loading = state.isLoading,
                            text = "Далее",
                            onClick = {
                                viewModel.send(event = SignInEvent.SignIn(login = login, password = password))
                            }
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SignInPreview() {
    val screenModel: SignInViewModel by di.instance()

    SignInView(viewModel = screenModel)
}