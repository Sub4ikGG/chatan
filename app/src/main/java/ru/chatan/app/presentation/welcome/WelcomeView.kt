package ru.chatan.app.presentation.welcome

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.LocalNavigator
import ru.chatan.app.R
import ru.chatan.app.presentation.elements.BasicButton
import ru.chatan.app.presentation.elements.basicClickable
import ru.chatan.app.presentation.signin.SignInScreen
import ru.chatan.app.presentation.signup.SignUpScreen
import ru.chatan.app.presentation.theme.ChatanTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WelcomeView() {
    val navigator = LocalNavigator.current

    Scaffold(modifier = Modifier.fillMaxSize()) { contentPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(contentPadding)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Spacer(modifier = Modifier.weight(1f))
                Spacer(modifier = Modifier.weight(1f))

                Box(modifier = Modifier.padding(40.dp)) {
                    Image(
                        painter = painterResource(id = R.drawable.welcome_shape),
                        contentDescription = "Chatan shape"
                    )
                }

                Spacer(modifier = Modifier.weight(1f))

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp)
                        .height(IntrinsicSize.Max)
                ) {
                    Text(
                        text = "Легко.\nБыстро.\nБезопасно.",
                        style = MaterialTheme.typography.headlineLarge,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    Row(
                        modifier = Modifier
                    ) {
                        BasicButton(
                            modifier = Modifier
                                .weight(1f),
                            text = "Приступить",
                            onClick = {
                                navigator?.push(SignUpScreen())
                            }
                        )

                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxHeight(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "Есть аккаунт?",
                                textDecoration = TextDecoration.Underline,
                                color = Color.Black,
                                modifier = Modifier.basicClickable {
                                    navigator?.push(SignInScreen())
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun WelcomePreview() {
    ChatanTheme {
        WelcomeView()
    }
}