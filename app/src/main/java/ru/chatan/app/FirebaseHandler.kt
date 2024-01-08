package ru.chatan.app

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.media.AudioAttributes
import android.media.RingtoneManager
import android.net.Uri
import coil.ImageLoader
import coil.request.ImageRequest
import coil.request.SuccessResult
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FirebaseHandler : FirebaseMessagingService() {

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)

        val title = message.data["title"]
        val body = message.data["body"]
        val avatar = message.data["avatar"]

        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        val importance = setImportance()
        configureNotificationChannel(importance, defaultSoundUri, notificationManager)

        CoroutineScope(Dispatchers.IO).launch {
            val bitmap = getAvatarBitmap(avatar)

            val notification: Notification =
                createNotification(
                    title = title,
                    body = body,
                    bitmap = bitmap
                )

            notificationManager.notify(1, notification)

            DLog.d(TAG, "Receive message: ${message.data}")
        }
    }

    private suspend fun getAvatarBitmap(avatar: String?): Bitmap? {
        val loader = ImageLoader(applicationContext)
        val request = ImageRequest.Builder(applicationContext)
            .data(avatar)
            .allowHardware(false) // Disable hardware bitmaps.
            .build()

        val result = (loader.execute(request) as? SuccessResult)?.drawable
        return (result as? BitmapDrawable)?.bitmap
    }

    private fun setImportance(): Int {
        return NotificationManager.IMPORTANCE_HIGH
    }

    private fun createNotification(
        title: String?,
        body: String?,
        bitmap: Bitmap? = null
    ) =
        Notification.Builder(applicationContext, getString(R.string.app_name))
            .setLargeIcon(bitmap)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle(title)
            .setContentText(body)
            .setAutoCancel(true)
            .setChannelId(getString(R.string.app_name))
            .build()

    private fun configureNotificationChannel(
        importance: Int,
        defaultSoundUri: Uri?,
        notificationManager: NotificationManager
    ) {
        val mChannel = NotificationChannel(
            getString(R.string.app_name),
            getString(R.string.app_name),
            importance
        )
        mChannel.setSound(defaultSoundUri, AudioAttributes.Builder().build())
        notificationManager.createNotificationChannel(mChannel)
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)

        DLog.d(TAG, "onNewToken: $token")
    }

    private companion object {
        const val TAG = "FirebaseHandler"
    }

}