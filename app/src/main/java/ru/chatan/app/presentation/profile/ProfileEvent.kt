package ru.chatan.app.presentation.profile

sealed class ProfileEvent {
    object GetUser : ProfileEvent()
    class UploadAvatar(val uploadAvatar: ru.chatan.app.domain.models.user.UploadAvatar) : ProfileEvent()
}