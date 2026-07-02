package com.djk.opener.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.djk.opener.database.DatabaseProvider
import com.djk.opener.models.ParentSettings
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class ParentSettingsViewModel(application: Application) : AndroidViewModel(application) {
    private val database = DatabaseProvider.getInstance(application)
    private val settingsDao = database.parentSettingsDao()

    val settings: Flow<ParentSettings?> = settingsDao.getSettings()

    fun updatePassword(newPassword: String) {
        viewModelScope.launch {
            settingsDao.updatePassword(newPassword, System.currentTimeMillis())
        }
    }

    fun updateAdminStatus(enabled: Boolean) {
        viewModelScope.launch {
            settingsDao.updateAdminStatus(enabled)
        }
    }

    fun updateBiometricStatus(enabled: Boolean) {
        viewModelScope.launch {
            settingsDao.updateBiometricStatus(enabled)
        }
    }

    fun updateNotificationsStatus(enabled: Boolean) {
        viewModelScope.launch {
            settingsDao.updateNotificationsStatus(enabled)
        }
    }

    fun updateBlockSchedule(enabled: Boolean, startTime: String, endTime: String) {
        viewModelScope.launch {
            settingsDao.updateBlockSchedule(enabled, startTime, endTime)
        }
    }

    class Factory(private val application: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return if (modelClass.isAssignableFrom(ParentSettingsViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                ParentSettingsViewModel(application) as T
            } else {
                throw IllegalArgumentException("Unknown ViewModel class")
            }
        }
    }
}
