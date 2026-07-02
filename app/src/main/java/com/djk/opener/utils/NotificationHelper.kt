package com.djk.opener.utils

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import com.djk.opener.R

class NotificationHelper(private val context: Context) {
    private val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    init {
        createNotificationChannels()
    }

    private fun createNotificationChannels() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Canal pour les notifications de blocage
            val blockerChannel = NotificationChannel(
                "blocker_channel",
                "Notifications de Blocage",
                NotificationManager.IMPORTANCE_LOW
            ).apply {
                description = "Notifications quand une app bridée est accédée"
            }
            notificationManager.createNotificationChannel(blockerChannel)

            // Canal pour les notifications parentes
            val parentChannel = NotificationChannel(
                "parent_channel",
                "Notifications Parent",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "Notifications importantes pour le parent"
            }
            notificationManager.createNotificationChannel(parentChannel)

            // Canal pour les notifications générales
            val generalChannel = NotificationChannel(
                "general_channel",
                "Notifications Générales",
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = "Notifications générales de l'app"
            }
            notificationManager.createNotificationChannel(generalChannel)
        }
    }

    fun showBlockedNotification(appName: String, packageName: String) {
        val notification = NotificationCompat.Builder(context, "blocker_channel")
            .setContentTitle("App Bridée")
            .setContentText("$appName a été bloquée")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .build()

        notificationManager.notify(appName.hashCode(), notification)
    }

    fun showParentAlert(title: String, message: String) {
        val notification = NotificationCompat.Builder(context, "parent_channel")
            .setContentTitle(title)
            .setContentText(message)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .build()

        notificationManager.notify(title.hashCode(), notification)
    }

    fun showGeneralNotification(title: String, message: String) {
        val notification = NotificationCompat.Builder(context, "general_channel")
            .setContentTitle(title)
            .setContentText(message)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()

        notificationManager.notify(title.hashCode(), notification)
    }

    fun cancelNotification(notificationId: Int) {
        notificationManager.cancel(notificationId)
    }
}
