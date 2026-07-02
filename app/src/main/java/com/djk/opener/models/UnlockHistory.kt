package com.djk.opener.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "unlock_history")
data class UnlockHistory(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val packageName: String,
    val appName: String,
    val unlockedAt: Long = System.currentTimeMillis(),
    val unlockedBy: String = "PARENT",
    val unlockedUntil: Long? = null,
    val reason: String = ""
)
