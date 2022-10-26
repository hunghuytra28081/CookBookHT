package com.example.cookbookht.data.repository.source.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "history")
data class History(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id :Int = 0,
    @ColumnInfo(name = "querySearch")
    val history: String
)