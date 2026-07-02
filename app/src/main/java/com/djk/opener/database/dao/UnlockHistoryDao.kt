package com.djk.opener.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.djk.opener.models.UnlockHistory
import kotlinx.coroutines.flow.Flow

@Dao
interface UnlockHistoryDao {
    @Query("SELECT * FROM unlock_history ORDER BY unlockedAt DESC LIMIT 50")
    fun getRecentUnlocks(): Flow<List<UnlockHistory>>

    @Query("SELECT * FROM unlock_history WHERE packageName = :packageName ORDER BY unlockedAt DESC")
    fun getUnlocksForApp(packageName: String): Flow<List<UnlockHistory>>

    @Query("SELECT * FROM unlock_history WHERE unlockedAt >= :startTime AND unlockedAt <= :endTime ORDER BY unlockedAt DESC")
    fun getUnlocksByDateRange(startTime: Long, endTime: Long): Flow<List<UnlockHistory>>

    @Insert
    suspend fun insertUnlock(unlock: UnlockHistory)

    @Update
    suspend fun updateUnlock(unlock: UnlockHistory)

    @Delete
    suspend fun deleteUnlock(unlock: UnlockHistory)

    @Query("SELECT COUNT(*) FROM unlock_history WHERE unlockedAt >= :startTime")
    fun getUnlockCountSince(startTime: Long): Flow<Int>

    @Query("DELETE FROM unlock_history WHERE unlockedAt < :beforeTime")
    suspend fun deleteOldUnlocks(beforeTime: Long)
}
