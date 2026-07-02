package com.djk.opener.utils

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey

class EncryptionHelper(context: Context) {
    private val masterKey = MasterKey.Builder(context)
        .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
        .build()

    private val encryptedPrefs: SharedPreferences = EncryptedSharedPreferences.create(
        context,
        "djk_encrypted_prefs",
        masterKey,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    fun savePassword(password: String) {
        val hashedPassword = hashPassword(password)
        encryptedPrefs.edit().putString("parent_password", hashedPassword).apply()
    }

    fun verifyPassword(inputPassword: String): Boolean {
        val storedHash = encryptedPrefs.getString("parent_password", null) ?: return false
        val inputHash = hashPassword(inputPassword)
        return storedHash == inputHash
    }

    fun getPassword(): String? {
        return encryptedPrefs.getString("parent_password", null)
    }

    fun saveAdminStatus(isEnabled: Boolean) {
        encryptedPrefs.edit().putBoolean("admin_enabled", isEnabled).apply()
    }

    fun getAdminStatus(): Boolean {
        return encryptedPrefs.getBoolean("admin_enabled", false)
    }

    fun saveBiometricStatus(isEnabled: Boolean) {
        encryptedPrefs.edit().putBoolean("biometric_enabled", isEnabled).apply()
    }

    fun getBiometricStatus(): Boolean {
        return encryptedPrefs.getBoolean("biometric_enabled", false)
    }

    companion object {
        private fun hashPassword(password: String): String {
            return password.hashCode().toString()
        }
    }
}
