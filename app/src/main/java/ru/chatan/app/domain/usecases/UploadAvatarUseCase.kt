package ru.chatan.app.domain.usecases

import ru.chatan.app.data.models.user.UserAvatarDTO
import ru.chatan.app.domain.models.user.UploadAvatar
import ru.chatan.app.domain.models.user.UserAvatar
import ru.chatan.app.domain.repository.UserRepository
import ru.efremovkirill.ktorhandler.Response

class UploadAvatarUseCase(
    val repository: UserRepository
) {

    suspend operator fun invoke(uploadAvatar: UploadAvatar): Response<UserAvatar?> =
        repository.uploadAvatar(uploadAvatar = uploadAvatar).map()

    private fun Response<UserAvatarDTO?>.map(): Response<UserAvatar?> =
        Response(
            code = this.code,
            responseMessage = this.message,
            data = this.data?.toModel(),
            errors = this.errors
        )
}