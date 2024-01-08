package ru.chatan.app.data.repository

import ru.chatan.app.data.models.user.UserAvatarDTO
import ru.chatan.app.domain.models.user.UploadAvatar
import ru.chatan.app.domain.repository.UserRepository
import ru.efremovkirill.ktorhandler.KtorClient
import ru.efremovkirill.ktorhandler.Response

class UserRepositoryImpl : UserRepository {

    override suspend fun uploadAvatar(uploadAvatar: UploadAvatar): Response<UserAvatarDTO?> =
        KtorClient.postSafely(
            path = UPLOAD_AVATAR_PATH,
            body = uploadAvatar.toDTO()
        )

    private companion object {
        const val UPLOAD_AVATAR_PATH = "/avatar/upload"
    }
}