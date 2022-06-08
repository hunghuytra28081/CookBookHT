package com.example.cookbookht.data.repository.source.remote

import com.example.cookbookht.data.repository.source.RecipeDataSource
import com.example.cookbookht.data.repository.source.remote.api.APIService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RecipeRemoteDataSource(private val apiService: APIService) : RecipeDataSource.Remote {

    override suspend fun getRecipe(number: Int) = withContext(Dispatchers.IO) {
        apiService.getRecipe(number = number)
    }

    override suspend fun getRecipeDetail(id: Int?) = withContext(Dispatchers.IO) {
        apiService.getRecipeDetail(id)
    }

    override suspend fun searchRecipe(keyword: String, number: Int) = withContext(Dispatchers.IO){
        apiService.searchRecipe(keyword, number)
    }
}
