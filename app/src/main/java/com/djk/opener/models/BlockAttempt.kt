package com.djk.opener.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "block_attempts")
data class BlockAttempt(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val packageName: String,
    val appName: String,
    val attemptTime: Long = System.currentTimeMillis(),
    val attemptCount: Int = 1,
    val status: String = "BLOCKED" // BLOCKED, ALLOWED
)
