package ru.chatan.app.presentation.chats

import ru.chatan.app.domain.models.chat.Chat

data class ChatsState(
    val isLoading: Boolean,
    val chats: List<Chat>,
    val error: String? = null
) {
    fun success(chats: List<Chat>) =
        this.copy(
            isLoading = false,
            chats = chats,
            error = null
        )

    fun loading() =
        this.copy(
            isLoading = true,
            error = null
        )

    fun error(error: String) =
        this.copy(
            isLoading = false,
            error = error
        )

    companion object {
        fun initial() = ChatsState(
            isLoading = true,
            chats = emptyList(),
            error = null
        )
    }
}