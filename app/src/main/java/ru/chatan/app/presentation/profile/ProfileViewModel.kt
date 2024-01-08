package ru.chatan.app.presentation.profile

import ru.chatan.app.StateViewModel
import ru.chatan.app.domain.models.user.UploadAvatar
import ru.chatan.app.domain.usecases.UploadAvatarUseCase
import ru.chatan.app.presentation.User

class ProfileViewModel(
    private val uploadAvatarUseCase: UploadAvatarUseCase
) : StateViewModel<ProfileState, ProfileEvent>(ProfileState.initial()) {

    init {
        send(event = ProfileEvent.GetUser)
    }

    override fun send(event: ProfileEvent) = run {
        when (event) {
            ProfileEvent.GetUser -> getUser()
            is ProfileEvent.UploadAvatar -> uploadAvatar(uploadAvatar = event.uploadAvatar)
        }
    }

    private suspend fun getUser() {
        val user = User.user
        if (user != null) {
            mutableState.emit(getStateValue().successUser(user = user))
        } else mutableState.emit(getStateValue().error(error = "User not loaded"))
    }

    private suspend fun uploadAvatar(uploadAvatar: UploadAvatar) {
        mutableState.emit(getStateValue().loading())

        val response = uploadAvatarUseCase(uploadAvatar = uploadAvatar)
        if (response.isSuccess()) {
            val userAvatar = response.data ?: return
            val user = User.user?.copy(avatar = userAvatar) ?: return
            User.user = user

            mutableState.emit(getStateValue().successUser(user = user))
        }
        else mutableState.emit(getStateValue().error(error = response.message))
    }

}