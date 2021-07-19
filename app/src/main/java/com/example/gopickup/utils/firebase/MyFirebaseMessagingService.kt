package com.example.gopickup.utils.firebase

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import com.example.gopickup.R
import com.example.gopickup.presentation.main.MainActivity
import com.example.gopickup.utils.Constants
import com.example.gopickup.utils.SharedPreference
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessagingService : FirebaseMessagingService() {

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        Log.d("TAG", "onMessageReceived: FROM: ${remoteMessage.from}")
        Log.d("TAG", "onMessageReceived DATA: ${remoteMessage.data}")

        if (remoteMessage.data.isNotEmpty()) {
            val data: Map<String, String> = remoteMessage.data
            for ((key, value) in remoteMessage.data.entries) {
                Log.d("TAG", "onMessageReceived: key: $key, value: $value")
            }
            Log.d("TAG", "Message data payload: $data")

            val title = data["title"]
            val message = data["body"]

            showNotification(title!!, message!!, data)
        } else if (remoteMessage.notification != null) {
            Log.d("TAG", "onMessageReceived: BODY: ${remoteMessage.notification!!.body!!}")
            showNotification(
                remoteMessage.notification!!.title!!,
                remoteMessage.notification!!.body!!,
                remoteMessage.data
            )

        }
    }

    override fun onNewToken(token: String) {
        val sharedPreference = SharedPreference(this)
        sharedPreference.saveString(Constants.KEY_FCM_TOKEN, token)

        sendRegistrationToServer(token, object : NotificationListener {
            override fun onNewToken(token: String) {
                Log.d("TAG", "onNewToken: $token")
            }

        })
    }

    fun sendRegistrationToServer(token: String, listener: NotificationListener) {
        listener.onNewToken(token)
        Log.d("TAG", "saveRegistration: REFRESH TOKEN: $token")
    }

    private fun showNotification(title: String, message: String, data: Map<String, String>) {
        showNotification(title, message, "default", data)
    }

    private fun showNotification(
        title: String,
        message: String,
        type: String,
        data: Map<String, String>
    ) {
        val CHANNEL_ID = getString(R.string.default_notification_channel_id)
        val channelName = getString(R.string.default_notification_channel_name)

        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val description = "c3a apps"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, channelName, importance)
            channel.description = description
            channel.setShowBadge(true)
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            val notificationManager = getSystemService(
                NotificationManager::class.java
            )
            notificationManager?.createNotificationChannel(channel)
        }

//        // Create an Intent for the activity you want to start
        val intent = Intent(this, MainActivity::class.java)
//        intent.putExtra(KEY_IS_FROM_NOTIFICATION, true)
//        intent.putExtra(KEY_ID, data["id"])
//        intent.putExtra(KEY_NOTIF_TYPE, data["type"])
//        intent.putExtra(KEY_LIFELONGLEARNING_POST_TYPE, data["lifelonglearning_post_type"])
//        intent.putExtra(KEY_ELEARNING_TYPE, data["e_learning_type_name"])
//        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP)
//
        val notificationId = (System.currentTimeMillis() / 4).toInt()
        val pendingIntent =
            PendingIntent.getActivity(this, notificationId, intent, PendingIntent.FLAG_UPDATE_CURRENT)

        val largeIcon = BitmapFactory.decodeResource(resources, R.mipmap.ic_launcher)
        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.img_logo_new)
            .setBadgeIconType(NotificationCompat.BADGE_ICON_SMALL)
            .setLargeIcon(largeIcon)
            .setColor(ContextCompat.getColor(this, R.color.redPrimary))
            .setContentTitle(title)
            .setStyle(NotificationCompat.BigTextStyle().bigText(message))
            .setContentText(message)
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)

        val notificationManager = NotificationManagerCompat.from(this)
        notificationManager.notify(notificationId, builder.build())
    }

}