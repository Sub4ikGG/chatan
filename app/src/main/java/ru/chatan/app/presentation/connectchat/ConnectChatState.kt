package ru.chatan.app.presentation.connectchat

import ru.chatan.app.domain.models.chat.Chat

data class ConnectChatState(
    val isLoading: Boolean = false,
    val chat: Chat? = null,
    val error: String? = null,
) {
    fun loading() = this.copy(isLoading = true, error = null, chat = null)

    fun success(chat: Chat) = this.copy(isLoading = false, chat = chat, error = null)

    fun error(error: String) = this.copy(isLoading = false, chat = null, error = error)

    companion object {
        fun initial() = ConnectChatState()
    }
}