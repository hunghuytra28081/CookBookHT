package com.example.cookbookht.data.repository.source.local

import androidx.room.*
import com.example.cookbookht.data.repository.source.local.entities.Favorite
import com.example.cookbookht.data.repository.source.local.entities.History
import com.example.cookbookht.data.repository.source.local.entities.User

@Dao
interface RecipeDao {

    @Query("SELECT * FROM history")
    suspend fun getAllHistory(): List<History>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: History)

    @Query("DELETE FROM history WHERE querySearch = :name")
    fun deleteName(name: String)

    @Query("SELECT * FROM history WHERE querySearch =:name")
    fun isExists(name: String): History?

    //Favorite
    @Query("SELECT * FROM favorite")
    suspend fun getAllFavorite(): List<Favorite>

    @Insert
    suspend fun insertFavorite(imageLocal: Favorite)

    @Delete
    suspend fun deleteFavorite(favorite: Favorite)

    @Query("SELECT * FROM favorite WHERE id LIKE :id")
    suspend fun isFavorite(id: Int?): Favorite

    //Login

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: User)

    @Query("SELECT * FROM user WHERE userName = :userName AND password = :pass")
    fun login(userName: String, pass: String): User?
}