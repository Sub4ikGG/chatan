package ru.chatan.app.data.models.firebase

import kotlinx.serialization.Serializable

@Serializable
data class FirebaseTokenDTO(
    val token: String
)
