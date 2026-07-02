package com.djk.opener.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "blocked_apps")
data class BlockedApp(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val packageName: String,
    val appName: String,
    val appIcon: ByteArray?,
    val isBlocked: Boolean = true,
    val blockReason: String = "Réseau social",
    val dateAdded: Long = System.currentTimeMillis(),
    val blockedUntil: Long? = null,
    val category: String = "SOCIAL_MEDIA"
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as BlockedApp

        if (id != other.id) return false
        if (packageName != other.packageName) return false
        if (appName != other.appName) return false
        if (appIcon != null) {
            if (other.appIcon == null) return false
            if (!appIcon.contentEquals(other.appIcon)) return false
        } else if (other.appIcon != null) return false
        if (isBlocked != other.isBlocked) return false
        if (blockReason != other.blockReason) return false
        if (dateAdded != other.dateAdded) return false
        if (blockedUntil != other.blockedUntil) return false
        if (category != other.category) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + packageName.hashCode()
        result = 31 * result + appName.hashCode()
        result = 31 * result + (appIcon?.contentHashCode() ?: 0)
        result = 31 * result + isBlocked.hashCode()
        result = 31 * result + blockReason.hashCode()
        result = 31 * result + dateAdded.hashCode()
        result = 31 * result + (blockedUntil?.hashCode() ?: 0)
        result = 31 * result + category.hashCode()
        return result
    }
}
