package com.example.cookbookht.data.repository.source

import com.example.cookbookht.data.model.Recipe
import com.example.cookbookht.data.model.RecipeDetail
import com.example.cookbookht.data.model.RecipeResponse
import com.example.cookbookht.data.model.SearchRecipeResponse
import com.example.cookbookht.data.repository.source.local.History
import retrofit2.Response

interface RecipeDataSource {

    interface Remote {

        suspend fun getRecipe(number: Int): RecipeResponse

        suspend fun getRecipeDetail(id: Int?): RecipeDetail

        suspend fun searchRecipe (keyword: String, number: Int): SearchRecipeResponse

        suspend fun searchIngredient (keyword: String, number: Int): MutableList<Recipe>
    }

    interface Local{
        suspend fun getAllHistory() : List<History>

        suspend fun insertHistory(item: History)

        suspend fun deleteHistory(name: String)

        fun isExistHistory(name: String)
    }
}