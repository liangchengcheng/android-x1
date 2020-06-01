package com.hdsx.x1.model.room

import androidx.room.RoomDatabase

abstract class AppDatabase : RoomDatabase() {
    abstract fun readHistoryDao(): ReadHistoryDao
}