package com.djk.opener.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.djk.opener.models.ParentSettings
import kotlinx.coroutines.flow.Flow

@Dao
interface ParentSettingsDao {
    @Query("SELECT * FROM parent_settings WHERE id = 1 LIMIT 1")
    fun getSettings(): Flow<ParentSettings?>

    @Query("SELECT * FROM parent_settings WHERE id = 1 LIMIT 1")
    suspend fun getSettingsSync(): ParentSettings?

    @Insert
    suspend fun insertSettings(settings: ParentSettings)

    @Update
    suspend fun updateSettings(settings: ParentSettings)

    @Query("UPDATE parent_settings SET parentPassword = :password, lastPasswordChange = :timestamp WHERE id = 1")
    suspend fun updatePassword(password: String, timestamp: Long = System.currentTimeMillis())

    @Query("UPDATE parent_settings SET isAdminEnabled = :enabled WHERE id = 1")
    suspend fun updateAdminStatus(enabled: Boolean)

    @Query("UPDATE parent_settings SET biometricEnabled = :enabled WHERE id = 1")
    suspend fun updateBiometricStatus(enabled: Boolean)

    @Query("UPDATE parent_settings SET notificationsEnabled = :enabled WHERE id = 1")
    suspend fun updateNotificationsStatus(enabled: Boolean)

    @Query("UPDATE parent_settings SET blockScheduleEnabled = :enabled, blockStartTime = :startTime, blockEndTime = :endTime WHERE id = 1")
    suspend fun updateBlockSchedule(enabled: Boolean, startTime: String, endTime: String)
}
