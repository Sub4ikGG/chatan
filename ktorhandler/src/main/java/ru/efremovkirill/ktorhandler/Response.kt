package ru.efremovkirill.ktorhandler

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

private const val UNDEFINED_ERROR = "Неизвестная ошибка"
private const val TOKEN_ERROR = "Неверный токен авторизации"

@Serializable
data class Response<T>(
    @SerialName("code")
    val code: Int,
    @SerialName("message")
    private val responseMessage: String = "",
    @SerialName("data")
    val data: T? = null,
    @SerialName("errors")
    val errors: List<Error>? = null
) {
    val message: String get() = getError().ifEmpty { responseMessage }.ifEmpty { if (code == 401) TOKEN_ERROR else UNDEFINED_ERROR }

    fun isSuccess() = code == 200

    fun getError() = errors?.joinToString(separator = "\n") { it.message } ?: responseMessage

    companion object {
        fun <T> empty(): Response<T?> = Response(
            code = 999,
            responseMessage = "Неизвестная ошибка, попробуйте позже!",
            data = null
        )
    }
}

@Serializable
data class Error(
    val fieldName: String,
    val message: String
)