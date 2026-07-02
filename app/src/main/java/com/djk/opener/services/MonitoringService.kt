package com.djk.opener.services

import android.app.Service
import android.app.usage.UsageStatsManager
import android.content.Context
import android.content.Intent
import android.os.IBinder
import com.djk.opener.database.DatabaseProvider
import com.djk.opener.models.BlockAttempt
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MonitoringService : Service() {
    private val scope = CoroutineScope(Dispatchers.Default)
    private var isMonitoring = false

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (!isMonitoring) {
            isMonitoring = true
            startMonitoring()
        }
        return START_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? = null

    private fun startMonitoring() {
        scope.launch {
            while (isMonitoring) {
                try {
                    checkAppUsage()
                    delay(1000) // Vérification toutes les secondes
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }

    private suspend fun checkAppUsage() {
        val usageStatsManager = getSystemService(Context.USAGE_STATS_SERVICE) as UsageStatsManager
        val db = DatabaseProvider.getInstance(applicationContext)
        val blockedAppDao = db.blockedAppDao()
        val blockAttemptDao = db.blockAttemptDao()

        val currentTime = System.currentTimeMillis()
        val startTime = currentTime - 3600000 // Dernière heure

        try {
            val usageStats = usageStatsManager.queryUsageStats(
                UsageStatsManager.INTERVAL_BEST,
                startTime,
                currentTime
            ) ?: return

            usageStats.sortByDescending { it.lastTimeUsed }
            val mostRecentApp = usageStats.firstOrNull() ?: return

            val blockedApp = blockedAppDao.getBlockedAppByPackage(mostRecentApp.packageName)

            if (blockedApp?.isBlocked == true) {
                val attempt = BlockAttempt(
                    packageName = mostRecentApp.packageName,
                    appName = blockedApp.appName,
                    status = "BLOCKED"
                )
                blockAttemptDao.insertBlockAttempt(attempt)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        isMonitoring = false
    }
}
