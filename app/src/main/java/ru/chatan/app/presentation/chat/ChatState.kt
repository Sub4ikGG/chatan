package ru.chatan.app.presentation.chat

import ru.chatan.app.domain.models.message.ChatMessage

data class ChatState(
    val isLoading: Boolean,
    val messages: List<ChatMessage>,
    val error: String? = null
) {
    fun loading() = this.copy(isLoading = true, error = null)

    fun error(error: String) = this.copy(isLoading = false, error = error)

    fun success(messages: List<ChatMessage>) = this.copy(isLoading = false, error = null, messages = messages)

    companion object {
        fun initial() = ChatState(
            isLoading = true,
            messages = emptyList(),
            error = null
        )
    }
}
