package com.djk.opener.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.djk.opener.database.DatabaseProvider
import com.djk.opener.models.BlockAttempt
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class BlockAttemptsViewModel(application: Application) : AndroidViewModel(application) {
    private val database = DatabaseProvider.getInstance(application)
    private val blockAttemptDao = database.blockAttemptDao()

    val recentAttempts: Flow<List<BlockAttempt>> = blockAttemptDao.getRecentBlockAttempts()

    fun getAttemptsForApp(packageName: String): Flow<List<BlockAttempt>> {
        return blockAttemptDao.getBlockAttemptsForApp(packageName)
    }

    fun getAttemptsInRange(startTime: Long, endTime: Long): Flow<List<BlockAttempt>> {
        return blockAttemptDao.getBlockAttemptsByDateRange(startTime, endTime)
    }

    fun getBlockedCountSince(timestamp: Long): Flow<Int> {
        return blockAttemptDao.getBlockedCountSince(timestamp)
    }

    fun deleteOldAttempts(beforeTime: Long) {
        viewModelScope.launch {
            blockAttemptDao.deleteOldAttempts(beforeTime)
        }
    }

    class Factory(private val application: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return if (modelClass.isAssignableFrom(BlockAttemptsViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                BlockAttemptsViewModel(application) as T
            } else {
                throw IllegalArgumentException("Unknown ViewModel class")
            }
        }
    }
}
