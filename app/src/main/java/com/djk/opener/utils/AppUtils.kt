package com.djk.opener.utils

import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager

class AppUtils(private val context: Context) {
    private val packageManager = context.packageManager

    fun getAppName(packageName: String): String {
        return try {
            val appInfo = packageManager.getApplicationInfo(packageName, 0)
            packageManager.getApplicationLabel(appInfo).toString()
        } catch (e: PackageManager.NameNotFoundException) {
            packageName
        }
    }

    fun isAppInstalled(packageName: String): Boolean {
        return try {
            packageManager.getApplicationInfo(packageName, 0)
            true
        } catch (e: PackageManager.NameNotFoundException) {
            false
        }
    }

    fun getInstalledApps(): List<ApplicationInfo> {
        return packageManager.getInstalledApplications(PackageManager.GET_META_DATA)
    }

    fun getSocialMediaApps(): List<String> {
        return listOf(
            "com.facebook.katana",
            "com.whatsapp",
            "com.instagram.android",
            "com.snapchat.android",
            "com.zhiliaoapp.musically", // TikTok
            "com.twitter.android",
            "com.google.android.youtube",
            "com.tencent.mm", // WeChat
            "com.viber.voip",
            "com.telegram.messenger"
        )
    }

    fun getInstalledSocialMediaApps(): List<String> {
        return getSocialMediaApps().filter { isAppInstalled(it) }
    }
}
