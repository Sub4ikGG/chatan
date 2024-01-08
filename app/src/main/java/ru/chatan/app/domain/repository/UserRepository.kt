package ru.chatan.app.domain.repository

import ru.chatan.app.data.models.user.UserAvatarDTO
import ru.chatan.app.domain.models.user.UploadAvatar
import ru.efremovkirill.ktorhandler.Response

interface UserRepository {

    suspend fun uploadAvatar(uploadAvatar: UploadAvatar): Response<UserAvatarDTO?>

}