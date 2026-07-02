package com.djk.opener.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "parent_settings")
data class ParentSettings(
    @PrimaryKey
    val id: Int = 1,
    val parentPassword: String = "etienne",
    val isAdminEnabled: Boolean = false,
    val biometricEnabled: Boolean = false,
    val notificationsEnabled: Boolean = true,
    val blockScheduleEnabled: Boolean = false,
    val blockStartTime: String = "07:00",
    val blockEndTime: String = "22:00",
    val customBlockMessage: String = "L'app a été bridée.\nVeuillez attendre que le parent vous autorise l'accès.",
    val lastPasswordChange: Long = System.currentTimeMillis()
)
