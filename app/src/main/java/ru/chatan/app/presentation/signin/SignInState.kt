package ru.chatan.app.presentation.signin

data class SignInState(
    var isLoading: Boolean = false,
    var isSignInSuccess: Boolean? = null,
    var error: String? = null
) {

    fun loading() =
        this.copy(isLoading = true)

    fun error(errorMessage: String) =
        this.copy(isLoading = false, isSignInSuccess = false, error = errorMessage)

    fun success() =
        this.copy(isLoading = false, isSignInSuccess = true)

    companion object {
        fun initial() = SignInState(
            isLoading = false
        )
    }
}