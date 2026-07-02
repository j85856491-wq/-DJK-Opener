package com.djk.opener.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.djk.opener.models.BlockedApp
import kotlinx.coroutines.flow.Flow

@Dao
interface BlockedAppDao {
    @Query("SELECT * FROM blocked_apps ORDER BY dateAdded DESC")
    fun getAllBlockedApps(): Flow<List<BlockedApp>>

    @Query("SELECT * FROM blocked_apps WHERE isBlocked = 1 ORDER BY dateAdded DESC")
    fun getActiveBlockedApps(): Flow<List<BlockedApp>>

    @Query("SELECT * FROM blocked_apps WHERE packageName = :packageName LIMIT 1")
    suspend fun getBlockedAppByPackage(packageName: String): BlockedApp?

    @Query("SELECT * FROM blocked_apps WHERE category = :category ORDER BY dateAdded DESC")
    fun getBlockedAppsByCategory(category: String): Flow<List<BlockedApp>>

    @Insert
    suspend fun insertBlockedApp(app: BlockedApp)

    @Update
    suspend fun updateBlockedApp(app: BlockedApp)

    @Delete
    suspend fun deleteBlockedApp(app: BlockedApp)

    @Query("DELETE FROM blocked_apps WHERE packageName = :packageName")
    suspend fun deleteBlockedAppByPackage(packageName: String)

    @Query("SELECT COUNT(*) FROM blocked_apps WHERE isBlocked = 1")
    fun getBlockedAppsCount(): Flow<Int>

    @Query("SELECT DISTINCT category FROM blocked_apps")
    fun getAllCategories(): Flow<List<String>>
}
