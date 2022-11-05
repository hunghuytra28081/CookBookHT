package com.example.cookbookht.data.repository.source

import com.example.cookbookht.data.model.RecipeDetail
import com.example.cookbookht.data.model.RecipeResponse
import com.example.cookbookht.data.model.SearchRecipeResponse
import com.example.cookbookht.data.repository.source.local.entities.Favorite
import com.example.cookbookht.data.repository.source.local.entities.History

interface RecipeDataSource {

    interface Remote {

        suspend fun getRecipe(number: Int): RecipeResponse

        suspend fun getRecipeDetail(id: Int?): RecipeDetail

        suspend fun searchRecipe (keyword: String, number: Int): SearchRecipeResponse
    }

    interface Local{
        suspend fun getAllHistory() : List<History>

        suspend fun insertHistory(item: History)

        suspend fun deleteHistory(name: String)

        fun isExistHistory(name: String)

        suspend fun getAllFavorite() : List<Favorite>

        suspend fun insertFavorite(item: Favorite)

        suspend fun deleteFavorite(favorite: Favorite)

        suspend fun alreadyFavorite(id: Int?) : Favorite
    }
}