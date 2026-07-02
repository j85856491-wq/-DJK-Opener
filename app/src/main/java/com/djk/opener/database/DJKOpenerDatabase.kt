package com.djk.opener.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.djk.opener.database.dao.BlockAttemptDao
import com.djk.opener.database.dao.BlockedAppDao
import com.djk.opener.database.dao.ParentSettingsDao
import com.djk.opener.database.dao.UnlockHistoryDao
import com.djk.opener.models.BlockAttempt
import com.djk.opener.models.BlockedApp
import com.djk.opener.models.ParentSettings
import com.djk.opener.models.UnlockHistory

@Database(
    entities = [
        BlockedApp::class,
        BlockAttempt::class,
        ParentSettings::class,
        UnlockHistory::class
    ],
    version = 1,
    exportSchema = false
)
abstract class DJKOpenerDatabase : RoomDatabase() {
    abstract fun blockedAppDao(): BlockedAppDao
    abstract fun blockAttemptDao(): BlockAttemptDao
    abstract fun parentSettingsDao(): ParentSettingsDao
    abstract fun unlockHistoryDao(): UnlockHistoryDao
}
