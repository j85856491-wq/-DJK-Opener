package com.djk.opener.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.djk.opener.database.DatabaseProvider
import com.djk.opener.models.BlockedApp
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class BlockedAppsViewModel(application: Application) : AndroidViewModel(application) {
    private val database = DatabaseProvider.getInstance(application)
    private val blockedAppDao = database.blockedAppDao()

    val blockedApps: Flow<List<BlockedApp>> = blockedAppDao.getAllBlockedApps()
    val activeBlockedApps: Flow<List<BlockedApp>> = blockedAppDao.getActiveBlockedApps()
    val blockedAppsCount: Flow<Int> = blockedAppDao.getBlockedAppsCount()

    fun addBlockedApp(app: BlockedApp) {
        viewModelScope.launch {
            blockedAppDao.insertBlockedApp(app)
        }
    }

    fun updateBlockedApp(app: BlockedApp) {
        viewModelScope.launch {
            blockedAppDao.updateBlockedApp(app)
        }
    }

    fun deleteBlockedApp(app: BlockedApp) {
        viewModelScope.launch {
            blockedAppDao.deleteBlockedApp(app)
        }
    }

    fun deleteByPackage(packageName: String) {
        viewModelScope.launch {
            blockedAppDao.deleteBlockedAppByPackage(packageName)
        }
    }

    fun getBlockedAppsByCategory(category: String): Flow<List<BlockedApp>> {
        return blockedAppDao.getBlockedAppsByCategory(category)
    }

    class Factory(private val application: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return if (modelClass.isAssignableFrom(BlockedAppsViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                BlockedAppsViewModel(application) as T
            } else {
                throw IllegalArgumentException("Unknown ViewModel class")
            }
        }
    }
}
