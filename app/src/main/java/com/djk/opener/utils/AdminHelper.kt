package com.djk.opener.utils

import android.app.admin.DevicePolicyManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import com.djk.opener.receivers.AdminReceiver

class AdminHelper(private val context: Context) {
    private val devicePolicyManager = context.getSystemService(Context.DEVICE_POLICY_SERVICE) as DevicePolicyManager
    private val adminComponent = ComponentName(context, AdminReceiver::class.java)

    fun isAdminActive(): Boolean {
        return devicePolicyManager.isAdminActive(adminComponent)
    }

    fun requestAdminPermission() {
        val intent = Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN)
        intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, adminComponent)
        intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION, "DJK Opener a besoin des permissions Admin pour fonctionner")
        context.startActivity(intent)
    }

    fun removeAdminPermission() {
        devicePolicyManager.removeActiveAdmin(adminComponent)
    }

    fun lockDevice() {
        if (isAdminActive()) {
            devicePolicyManager.lockNow()
        }
    }
}
