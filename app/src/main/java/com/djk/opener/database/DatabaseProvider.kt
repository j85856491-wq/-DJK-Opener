package com.djk.opener.database

import android.content.Context
import androidx.room.Room

object DatabaseProvider {
    private var database: DJKOpenerDatabase? = null

    fun getInstance(context: Context): DJKOpenerDatabase {
        if (database == null) {
            synchronized(this) {
                database = Room.databaseBuilder(
                    context.applicationContext,
                    DJKOpenerDatabase::class.java,
                    "djk_opener_db"
                ).build()
            }
        }
        return database!!
    }

    fun closeDatabase() {
        database?.close()
        database = null
    }
}
