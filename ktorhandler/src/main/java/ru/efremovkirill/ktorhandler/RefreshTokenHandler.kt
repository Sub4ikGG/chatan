package ru.efremovkirill.ktorhandler

import android.util.Log
import ru.efremovkirill.ktorhandler.tokens.TokensDTO
import ru.efremovkirill.localstorage.LocalStorage

class RefreshTokenHandler {

    private companion object {
        private const val TAG = "RefreshTokenHandler"
    }

    suspend fun refreshTokens(): Boolean {
        Log.d(TAG, "refreshTokens")

        val localStorage = LocalStorage.newInstance()
        val token = localStorage.get(LocalStorage.TOKEN)
        val refreshToken = localStorage.get(LocalStorage.REFRESH_TOKEN)

        if (token == null || refreshToken == null) {
            return false
        }

        val tokens = TokensDTO(
            token = token,
            refreshToken = refreshToken
        )

        try {
            val newTokens: Response<TokensDTO?> = KtorClient.postSafely(
                host = KtorClient.TOKEN_REFRESH_HOST,
                path = KtorClient.TOKEN_REFRESH_PATH,
                body = tokens
            ) ?: Response.empty()

            val tokensDTO = newTokens.data ?: return false
            saveTokens(localStorage, tokensDTO)

            return true
        } catch (_: Exception) {
            return false
        }
    }

    private fun saveTokens(localStorage: LocalStorage, tokensDTO: TokensDTO) {
        localStorage.save(key = LocalStorage.TOKEN, data = tokensDTO.token)
        localStorage.save(key = LocalStorage.REFRESH_TOKEN, data = tokensDTO.refreshToken)
    }

}