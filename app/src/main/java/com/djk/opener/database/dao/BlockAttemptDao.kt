package com.djk.opener.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.djk.opener.models.BlockAttempt
import kotlinx.coroutines.flow.Flow

@Dao
interface BlockAttemptDao {
    @Query("SELECT * FROM block_attempts ORDER BY attemptTime DESC LIMIT 100")
    fun getRecentBlockAttempts(): Flow<List<BlockAttempt>>

    @Query("SELECT * FROM block_attempts WHERE packageName = :packageName ORDER BY attemptTime DESC")
    fun getBlockAttemptsForApp(packageName: String): Flow<List<BlockAttempt>>

    @Query("SELECT * FROM block_attempts WHERE attemptTime >= :startTime AND attemptTime <= :endTime ORDER BY attemptTime DESC")
    fun getBlockAttemptsByDateRange(startTime: Long, endTime: Long): Flow<List<BlockAttempt>>

    @Insert
    suspend fun insertBlockAttempt(attempt: BlockAttempt)

    @Update
    suspend fun updateBlockAttempt(attempt: BlockAttempt)

    @Delete
    suspend fun deleteBlockAttempt(attempt: BlockAttempt)

    @Query("SELECT COUNT(*) FROM block_attempts WHERE status = 'BLOCKED' AND attemptTime >= :startTime")
    fun getBlockedCountSince(startTime: Long): Flow<Int>

    @Query("DELETE FROM block_attempts WHERE attemptTime < :beforeTime")
    suspend fun deleteOldAttempts(beforeTime: Long)
}
