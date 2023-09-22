package ru.efremovkirill.ktorhandler.tokens

import com.google.gson.annotations.SerializedName

@kotlinx.serialization.Serializable
data class TokensDTO(
    @SerializedName("token")
    val token: String,
    @SerializedName("refreshToken")
    val refreshToken: String
)
