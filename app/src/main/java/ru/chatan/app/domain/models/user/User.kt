package ru.chatan.app.domain.models.user

data class User(
    val id: Long,
    val name: String,
    val avatar: UserAvatar
)
