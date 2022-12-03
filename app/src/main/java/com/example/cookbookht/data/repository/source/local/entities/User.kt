package com.example.cookbookht.data.repository.source.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class User (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id :Int = 0,
    @ColumnInfo(name = "fullName")
    val fullName: String,
    @ColumnInfo(name = "userName")
    val userName: String,
    @ColumnInfo(name = "password")
    val password: String
)