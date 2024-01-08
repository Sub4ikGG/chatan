package ru.chatan.app.presentation.profile

import ru.chatan.app.domain.models.user.User

data class ProfileState(
    val loading: Boolean = false,
    val user: User? = null,
    val error: String? = null
) {
    fun loading() = this.copy(loading = true, error = null)

    fun successUser(user: User) = this.copy(loading = false, user = user, error = null)

    fun error(error: String) = this.copy(loading = false, error = error)

    companion object {
        fun initial() = ProfileState(loading = true)
    }
}
