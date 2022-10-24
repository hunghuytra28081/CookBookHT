package com.example.cookbookht.data.repository.source.local

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface HistoryDao {

    @Query("SELECT * FROM history")
    suspend fun getAllHistory(): List<History>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: History)

    @Query("DELETE FROM history WHERE history = :name")
    fun deleteName(name: String)

    @Query("SELECT * FROM history WHERE history =:name")
    fun isExists(name: String): History?
}