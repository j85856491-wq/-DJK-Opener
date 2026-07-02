package com.djk.opener.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.app.admin.DevicePolicyManager
import com.djk.opener.services.BlockerService
import com.djk.opener.services.MonitoringService

class BootReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action == Intent.ACTION_BOOT_COMPLETED) {
            context?.let {
                // Redémarrer les services
                val blockerIntent = Intent(it, BlockerService::class.java)
                it.startService(blockerIntent)

                val monitoringIntent = Intent(it, MonitoringService::class.java)
                it.startService(monitoringIntent)
            }
        }
    }
}
