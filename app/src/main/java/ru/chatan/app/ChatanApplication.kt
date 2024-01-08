package ru.chatan.app

import android.app.Application
import com.google.firebase.ktx.Firebase
import com.google.firebase.ktx.initialize
import com.google.firebase.messaging.FirebaseMessaging
import io.ktor.http.URLProtocol
import ru.efremovkirill.ktorhandler.KtorClient
import ru.efremovkirill.localstorage.LocalStorage
import java.util.UUID

class ChatanApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        configureLocalStorage()
        configureKtor()
        configureDeviceId()
        configureFirebase()
    }

    private fun configureFirebase() {
        val localStorage = LocalStorage.newInstance()

        Firebase.initialize(applicationContext)
        FirebaseMessaging.getInstance().token.addOnSuccessListener { token: String? ->
            localStorage.save(key = FIREBASE_TOKEN, data = token.orEmpty())

            DLog.d(TAG, "configureFirebase: $token")
        }.addOnFailureListener { failure ->
            DLog.e(TAG, "setFirebase: $failure")
        }
    }

    private fun configureDeviceId() {
        val localStorage = LocalStorage.newInstance()
        if (localStorage.has("deviceId")) return

        val deviceId = generateDeviceId()
        DLog.d(TAG, "generateDeviceId: $deviceId")

        localStorage.save("deviceId", deviceId)
    }

    private fun generateDeviceId() =
        UUID.nameUUIDFromBytes(System.currentTimeMillis().toString().toByteArray()).toString()

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
                    DLog.d(TAG, "onServiceStatusChanged: $status")
                }
            }
        )
    }

    companion object {
        private const val TAG = "ChatanApplication"

        private const val HOST = "api.chatan.ru"
        private const val TOKEN_REFRESH_HOST = "api.chatan.ru"
        private const val TOKEN_REFRESH_PATH = "/token-refresh"

        const val FIREBASE_TOKEN = "firebaseToken"
    }

}