package ru.chatan.app.presentation.start

data class StartState(
    val isSignInAutoSuccess: Boolean? = null,
    val error: String? = null
) {
    fun signInSuccess() = this.copy(isSignInAutoSuccess = true, error = null)
    fun cantSignIn() = this.copy(isSignInAutoSuccess = false, error = null)

    fun error(error: String) = this.copy(isSignInAutoSuccess = false, error = error)

    companion object {
        fun initial() = StartState()
    }
}