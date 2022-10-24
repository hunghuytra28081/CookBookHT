package com.example.cookbookht.data.repository.source.local.database

import android.content.Context
import androidx.room.Room
import com.example.cookbookht.utils.Constant

class DatabaseBuilder {

    fun buildRoomDB(context: Context) =
        Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java,
            Constant.DATABASE_NAME
        ).allowMainThreadQueries()
            .build()
}
