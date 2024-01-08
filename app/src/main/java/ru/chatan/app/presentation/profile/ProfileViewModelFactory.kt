package ru.chatan.app.presentation.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.chatan.app.domain.usecases.UploadAvatarUseCase

@Suppress("UNCHECKED_CAST")
class ProfileViewModelFactory(private val uploadAvatarUseCase: UploadAvatarUseCase) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProfileViewModel::class.java)) {
            return ProfileViewModel(uploadAvatarUseCase = uploadAvatarUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}