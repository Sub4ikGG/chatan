package ru.chatan.app.domain.models.chat

import ru.chatan.app.data.models.chat.CreateChatDTO

data class CreateChat(
    val code: String,
    val name: String,
    val description: String,
    val userLimit: Int
) {
    fun toDTO() = CreateChatDTO(
        code = code,
        name = name,
        description = description,
        userLimit = userLimit
    )
}