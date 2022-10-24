package com.example.cookbookht.data.repository.source.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.cookbookht.data.repository.source.local.History
import com.example.cookbookht.data.repository.source.local.HistoryDao

@Database(entities = [History::class], version = 1)
abstract class AppDatabase : RoomDatabase(){

    abstract val historyDao: HistoryDao
}
