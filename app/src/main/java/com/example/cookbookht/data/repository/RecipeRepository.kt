package com.example.cookbookht.data.repository

import com.example.cookbookht.data.repository.source.RecipeDataSource
import com.example.cookbookht.data.repository.source.local.History

class RecipeRepository(
    private val remote: RecipeDataSource.Remote,
    private val local: RecipeDataSource.Local
) {
    suspend fun getRecipe(number: Int) = remote.getRecipe(number)

    suspend fun getRecipeDetail(id: Int?) = remote.getRecipeDetail(id)

    suspend fun searchRecipe(keyword: String, number: Int) = remote.searchRecipe(keyword, number)

    suspend fun searchIngredient(keyword: String, number: Int) = remote.searchIngredient(keyword, number)

    suspend fun getAllHistory() = local.getAllHistory()

    suspend fun insertHistory(item: History) = local.insertHistory(item)

    suspend fun deleteHistory(name: String) = local.deleteHistory(name)

    fun isExistHistory(name: String) = local.isExistHistory(name)
}
