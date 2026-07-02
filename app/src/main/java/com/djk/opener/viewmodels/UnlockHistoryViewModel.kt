package com.djk.opener.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.djk.opener.database.DatabaseProvider
import com.djk.opener.models.UnlockHistory
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class UnlockHistoryViewModel(application: Application) : AndroidViewModel(application) {
    private val database = DatabaseProvider.getInstance(application)
    private val unlockHistoryDao = database.unlockHistoryDao()

    val recentUnlocks: Flow<List<UnlockHistory>> = unlockHistoryDao.getRecentUnlocks()

    fun getUnlocksForApp(packageName: String): Flow<List<UnlockHistory>> {
        return unlockHistoryDao.getUnlocksForApp(packageName)
    }

    fun getUnlocksInRange(startTime: Long, endTime: Long): Flow<List<UnlockHistory>> {
        return unlockHistoryDao.getUnlocksByDateRange(startTime, endTime)
    }

    fun getUnlockCountSince(timestamp: Long): Flow<Int> {
        return unlockHistoryDao.getUnlockCountSince(timestamp)
    }

    fun addUnlock(unlock: UnlockHistory) {
        viewModelScope.launch {
            unlockHistoryDao.insertUnlock(unlock)
        }
    }

    fun deleteOldUnlocks(beforeTime: Long) {
        viewModelScope.launch {
            unlockHistoryDao.deleteOldUnlocks(beforeTime)
        }
    }

    class Factory(private val application: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return if (modelClass.isAssignableFrom(UnlockHistoryViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                UnlockHistoryViewModel(application) as T
            } else {
                throw IllegalArgumentException("Unknown ViewModel class")
            }
        }
    }
}
