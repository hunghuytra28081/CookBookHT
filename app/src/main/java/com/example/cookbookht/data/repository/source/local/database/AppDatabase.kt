package com.example.cookbookht.data.repository.source.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.cookbookht.data.repository.source.local.entities.History
import com.example.cookbookht.data.repository.source.local.RecipeDao
import com.example.cookbookht.data.repository.source.local.entities.Favorite
import com.example.cookbookht.data.repository.source.local.entities.User

@Database(entities = [History::class, Favorite::class, User::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract val recipeDao: RecipeDao
}
