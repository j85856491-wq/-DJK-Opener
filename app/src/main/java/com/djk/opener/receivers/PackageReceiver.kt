package com.djk.opener.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.djk.opener.database.DatabaseProvider
import com.djk.opener.models.BlockedApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PackageReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val packageName = intent?.data?.schemeSpecificPart ?: return

        when (intent.action) {
            Intent.ACTION_PACKAGE_ADDED -> onPackageAdded(context, packageName)
            Intent.ACTION_PACKAGE_REMOVED -> onPackageRemoved(context, packageName)
            Intent.ACTION_PACKAGE_REPLACED -> onPackageReplaced(context, packageName)
        }
    }

    private fun onPackageAdded(context: Context?, packageName: String) {
        // Vérifier si c'est une app de réseau social et l'ajouter automatiquement
        val socialMediaPackages = listOf(
            "com.facebook.katana",
            "com.whatsapp",
            "com.instagram.android",
            "com.snapchat.android",
            "com.zhiliaoapp.musically",
            "com.twitter.android"
        )

        if (packageName in socialMediaPackages && context != null) {
            CoroutineScope(Dispatchers.IO).launch {
                val db = DatabaseProvider.getInstance(context)
                val dao = db.blockedAppDao()
                val app = BlockedApp(
                    packageName = packageName,
                    appName = packageName,
                    appIcon = null,
                    category = "SOCIAL_MEDIA"
                )
                dao.insertBlockedApp(app)
            }
        }
    }

    private fun onPackageRemoved(context: Context?, packageName: String) {
        if (context != null) {
            CoroutineScope(Dispatchers.IO).launch {
                val db = DatabaseProvider.getInstance(context)
                val dao = db.blockedAppDao()
                dao.deleteBlockedAppByPackage(packageName)
            }
        }
    }

    private fun onPackageReplaced(context: Context?, packageName: String) {
        // L'app a été mise à jour, on ne fait rien de particulier
    }
}
