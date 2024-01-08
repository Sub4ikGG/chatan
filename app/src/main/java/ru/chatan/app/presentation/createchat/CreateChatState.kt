package ru.chatan.app.presentation.createchat

import ru.chatan.app.domain.models.chat.Chat

data class CreateChatState(
    val loading: Boolean = false,
    val error: String? = null,
    val chat: Chat? = null
) {
    fun loading() = this.copy(loading = true, error = null, chat = null)

    fun error(error: String) = this.copy(loading = false, error = error)

    fun success(chat: Chat) = this.copy(loading = false, error = null, chat = chat)

    companion object {
        fun initial() = CreateChatState()
    }
}
