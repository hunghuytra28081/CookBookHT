package com.example.cookbookht.module

import android.content.Context
import com.example.cookbookht.data.repository.source.local.database.AppDatabase
import com.example.cookbookht.data.repository.source.local.database.DatabaseBuilder
import org.koin.dsl.module

val storageModule = module {

    fun buildRoomDB(context: Context) = DatabaseBuilder().buildRoomDB(context)

    fun provideHistoryDao(appDatabase: AppDatabase) = appDatabase.recipeDao

    single { buildRoomDB(get()) }
    single { provideHistoryDao(get()) }
}
