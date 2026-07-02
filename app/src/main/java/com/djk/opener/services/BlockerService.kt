package com.djk.opener.services

import android.app.Service
import android.content.Intent
import android.content.pm.ApplicationInfo
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.djk.opener.R
import com.djk.opener.database.DatabaseProvider
import com.djk.opener.models.BlockedApp
import com.djk.opener.utils.NotificationHelper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BlockerService : Service() {
    private val scope = CoroutineScope(Dispatchers.Main)
    private lateinit var notificationHelper: NotificationHelper

    companion object {
        const val NOTIFICATION_ID = 1001
        val DEFAULT_BLOCKED_APPS = listOf(
            BlockedApp(
                packageName = "com.facebook.katana",
                appName = "Facebook",
                appIcon = null,
                category = "SOCIAL_MEDIA"
            ),
            BlockedApp(
                packageName = "com.whatsapp",
                appName = "WhatsApp",
                appIcon = null,
                category = "SOCIAL_MEDIA"
            ),
            BlockedApp(
                packageName = "com.instagram.android",
                appName = "Instagram",
                appIcon = null,
                category = "SOCIAL_MEDIA"
            ),
            BlockedApp(
                packageName = "com.snapchat.android",
                appName = "Snapchat",
                appIcon = null,
                category = "SOCIAL_MEDIA"
            ),
            BlockedApp(
                packageName = "com.zhiliaoapp.musically",
                appName = "TikTok",
                appIcon = null,
                category = "SOCIAL_MEDIA"
            ),
            BlockedApp(
                packageName = "com.twitter.android",
                appName = "Twitter",
                appIcon = null,
                category = "SOCIAL_MEDIA"
            ),
            BlockedApp(
                packageName = "com.snapchat.android",
                appName = "Snapchat",
                appIcon = null,
                category = "SOCIAL_MEDIA"
            )
        )
    }

    override fun onCreate() {
        super.onCreate()
        notificationHelper = NotificationHelper(this)
        initializeBlockedApps()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        startForeground(NOTIFICATION_ID, createNotification())
        return START_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? = null

    private fun initializeBlockedApps() {
        scope.launch(Dispatchers.IO) {
            val db = DatabaseProvider.getInstance(applicationContext)
            val dao = db.blockedAppDao()

            DEFAULT_BLOCKED_APPS.forEach { app ->
                if (dao.getBlockedAppByPackage(app.packageName) == null) {
                    dao.insertBlockedApp(app)
                }
            }
        }
    }

    private fun createNotification() = NotificationCompat.Builder(this, "blocker_channel")
        .setContentTitle("DJK Opener - Actif")
        .setContentText("Protection réseaux sociaux en cours...")
        .setSmallIcon(R.drawable.ic_launcher_foreground)
        .setPriority(NotificationCompat.PRIORITY_LOW)
        .build()

    override fun onDestroy() {
        super.onDestroy()
        scope.launch {
            DatabaseProvider.closeDatabase()
        }
    }
}
