package ru.efremovkirill.ktorhandler.tokens

import com.google.gson.annotations.SerializedName

@kotlinx.serialization.Serializable
data class RefreshTokenDTO(
    @SerializedName("refreshToken")
    val refreshToken: String
)
