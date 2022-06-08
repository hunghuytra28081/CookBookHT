package com.example.cookbookht.data.repository.source

import com.example.cookbookht.data.model.RecipeDetail
import com.example.cookbookht.data.model.RecipeResponse
import com.example.cookbookht.data.model.SearchRecipeResponse
import retrofit2.Response

interface RecipeDataSource {

    interface Remote {

        suspend fun getRecipe(number: Int): RecipeResponse

        suspend fun getRecipeDetail(id: Int?): RecipeDetail

        suspend fun searchRecipe (keyword: String, number: Int): SearchRecipeResponse
    }
}