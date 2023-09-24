package ru.chatan.app.presentation.signup

data class SignUpState(
    var isLoading: Boolean = false,
    var isSignUpSuccess: Boolean? = null,
    var error: String? = null
) {

    fun loading() =
        this.copy(isLoading = true, isSignUpSuccess = null, error = null)

    fun error(errorMessage: String) =
        this.copy(isLoading = false, isSignUpSuccess = false, error = errorMessage)

    fun success() =
        this.copy(isLoading = false, isSignUpSuccess = true, error = null)

    companion object {
        fun initial() = SignUpState(
            isLoading = false
        )
    }
}