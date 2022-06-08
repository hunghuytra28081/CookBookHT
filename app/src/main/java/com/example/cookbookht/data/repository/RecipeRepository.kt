package com.example.cookbookht.data.repository

import com.example.cookbookht.data.repository.source.RecipeDataSource

class RecipeRepository(
    private val remote: RecipeDataSource.Remote,
) {
    suspend fun getRecipe(number: Int) = remote.getRecipe(number)

    suspend fun getRecipeDetail(id: Int?) = remote.getRecipeDetail(id)

    suspend fun searchRecipe(keyword: String, number: Int) = remote.searchRecipe(keyword, number)
}
