package ru.chatan.app.presentation.profile

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cafe.adriel.voyager.navigator.LocalNavigator
import coil.compose.AsyncImage
import org.kodein.di.instance
import ru.chatan.app.di.di
import ru.chatan.app.domain.models.user.UploadAvatar
import ru.chatan.app.presentation.elements.BasicButton
import ru.chatan.app.presentation.elements.BasicTextFieldView
import ru.chatan.app.presentation.elements.BasicToolBarView
import ru.chatan.app.presentation.elements.basicClickable
import ru.chatan.app.presentation.theme.ChatanTheme
import java.io.ByteArrayOutputStream
import java.util.Base64


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileView(
    viewModel: ProfileViewModel
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    var login by remember { mutableStateOf(state.user?.name.orEmpty()) }

    val context = LocalContext.current
    val navigator = LocalNavigator.current

    val avatarLauncher = rememberLauncherForActivityResult(
        contract =
        ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        if (uri != null) {
            val base64 = uri.toBase64(context = context)

            viewModel.send(
                event = ProfileEvent.UploadAvatar(
                    uploadAvatar = UploadAvatar(base64 = base64)
                )
            )
        }
    }

    Scaffold { contentPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0x80F6F4F4))
                .padding(top = contentPadding.calculateTopPadding()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            BasicToolBarView(
                text = "Профиль",
                backButtonVisible = true,
                onBackPressed = {
                    navigator?.pop()
                })

            Box(
                modifier = Modifier
                    .width(IntrinsicSize.Min)
                    .padding(top = 16.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    AsyncImage(
                        model = state.user?.avatar?.href,
                        modifier = Modifier
                            .size(128.dp)
                            .clip(CircleShape)
                            .background(Color.White)
                            .basicClickable {
                                avatarLauncher.launch("image/*")
                            },
                        contentScale = ContentScale.Crop,
                        contentDescription = "${state.user?.name} avatar"
                    )
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 16.dp)
                    .clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
                    .background(Color.White)
                    .padding(
                        top = 16.dp,
                        start = 16.dp,
                        end = 16.dp,
                        bottom = 16.dp + contentPadding.calculateBottomPadding()
                    )
            ) {
                BasicTextFieldView(
                    modifier = Modifier.fillMaxWidth(),
                    text = login,
                    hint = "Логин",
                    onValueChange = {
                        login = it
                    },
                    onClear = { /*TODO*/ })

                BasicTextFieldView(
                    modifier = Modifier.fillMaxWidth(),
                    text = "",
                    hint = "Пол",
                    onValueChange = {

                    },
                    onClear = { /*TODO*/ })

                BasicTextFieldView(
                    modifier = Modifier.fillMaxWidth(),
                    text = "",
                    hint = "Дата рождения",
                    onValueChange = {

                    },
                    onClear = { /*TODO*/ })

                Spacer(modifier = Modifier.weight(1f))

                BasicButton(
                    modifier = Modifier.fillMaxWidth(),
                    text = "Сохранить",
                    loading = state.loading
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileViewPreview() {
    ChatanTheme {
        val viewModel: ProfileViewModel by di.instance()

        ProfileView(viewModel = viewModel)
    }
}

private fun Uri.toBase64(context: Context): String {
    val inputStream = context.contentResolver.openInputStream(this)
    val bytes = inputStream?.readBytes(); inputStream?.close()
    val bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes?.size ?: 0)

    val byteArrayOutputStream = ByteArrayOutputStream()
    bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
    val byteArray = byteArrayOutputStream.toByteArray()
    val base64 = Base64.getEncoder().encodeToString(byteArray)

    return base64
}