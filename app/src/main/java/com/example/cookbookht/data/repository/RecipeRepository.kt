package com.example.cookbookht.data.repository

import com.example.cookbookht.data.repository.source.RecipeDataSource
import com.example.cookbookht.data.repository.source.local.entities.Favorite
import com.example.cookbookht.data.repository.source.local.entities.History

class RecipeRepository(
    private val remote: RecipeDataSource.Remote,
    private val local: RecipeDataSource.Local
) {
    suspend fun getRecipe(number: Int) = remote.getRecipe(number)

    suspend fun getRecipeDetail(id: Int?) = remote.getRecipeDetail(id)

    suspend fun searchRecipe(keyword: String, number: Int) = remote.searchRecipe(keyword, number)

    suspend fun getAllHistory() = local.getAllHistory()

    suspend fun insertHistory(item: History) = local.insertHistory(item)

    suspend fun deleteHistory(name: String) = local.deleteHistory(name)

    fun isExistHistory(name: String) = local.isExistHistory(name)

    suspend fun getAllFavorite() = local.getAllFavorite()

    suspend fun insertFavorite(item: Favorite) = local.insertFavorite(item)

    suspend fun deleteFavorite(favorite: Favorite) = local.deleteFavorite(favorite)

    suspend fun alreadyFavorite(id: Int?) = local.alreadyFavorite(id)
}
