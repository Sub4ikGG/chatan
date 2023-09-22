package ru.efremovkirill.ktorhandler

import android.util.Log
import ru.efremovkirill.ktorhandler.tokens.TokensDTO
import ru.efremovkirill.localstorage.LocalStorage

class RefreshTokenHandler {

    private var isTokensUpdating = false
    private var wasPreviousRequestSuccess = false

    fun wasPreviousRequestSuccess() = wasPreviousRequestSuccess
    fun isTokensUpdating() = isTokensUpdating

    suspend fun refreshTokens(): Boolean {
        isTokensUpdating = true
        Log.e("token/refresh", "isTokensUpdating = true")

        val localStorage = LocalStorage.newInstance()
        val token = localStorage.get(LocalStorage.TOKEN)
        val refreshToken = localStorage.get(LocalStorage.REFRESH_TOKEN)

        if (token == null || refreshToken == null) {
            wasPreviousRequestSuccess = false
            return false
        }

        val tokens = TokensDTO(
            token = token,
            refreshToken = refreshToken
        )

        val newTokens: Response<TokensDTO?>

        return try {
            newTokens = KtorClient.postSafely(
                host = KtorClient.TOKEN_REFRESH_HOST,
                path = KtorClient.TOKEN_REFRESH_PATH,
                body = tokens
            ) ?: Response.empty()

            val tokensDTO = newTokens.data
            if (tokensDTO == null) {
                wasPreviousRequestSuccess = false
                return false
            }

            saveTokens(localStorage, tokensDTO)

            isTokensUpdating = false
            wasPreviousRequestSuccess = true
            true
        } catch (_: Exception) {
            isTokensUpdating = false
            wasPreviousRequestSuccess = false
            false
        }
    }

    private fun saveTokens(localStorage: LocalStorage, tokensDTO: TokensDTO) {
        localStorage.save(key = LocalStorage.TOKEN, data = tokensDTO.token)
        localStorage.save(key = LocalStorage.REFRESH_TOKEN, data = tokensDTO.refreshToken)
    }

}