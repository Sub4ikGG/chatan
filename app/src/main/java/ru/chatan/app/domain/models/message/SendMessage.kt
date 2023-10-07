package ru.chatan.app.domain.models.message

import ru.chatan.app.data.models.message.SendMessageDTO

data class SendMessage(
    val body: String
) {
    fun toDTO() = SendMessageDTO(
        body = body
    )
}
