package ru.chatan.app

import android.app.Application
import android.util.Log
import io.ktor.http.URLProtocol
import ru.efremovkirill.ktorhandler.KtorClient
import ru.efremovkirill.localstorage.LocalStorage

class ChatanApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        configureLocalStorage()
        configureKtor()
    }

    private fun configureLocalStorage() {
        LocalStorage.init(context = applicationContext)
    }

    private fun configureKtor() {
        KtorClient.initialize(
            host = HOST,
            protocol = URLProtocol.HTTPS,
            tokenRefreshHost = TOKEN_REFRESH_HOST,
            tokenRefreshPath = TOKEN_REFRESH_PATH,
            serviceStatusListener = object : KtorClient.ServiceStatusListener {
                override fun onServiceStatusChanged(status: Boolean) {
                    Log.i(TAG, "onServiceStatusChanged: $status")
                }
            }
        )
    }

    companion object {
        private const val TAG = "ChatanApplication"

        private const val HOST = "api.chatan.ru"
        private const val TOKEN_REFRESH_HOST = "api.chatan.ru"
        private const val TOKEN_REFRESH_PATH = "/token-refresh"
    }

}