package com.example.cookbookht.data.repository.source.remote.api

import com.example.cookbookht.data.model.RecipeDetail
import com.example.cookbookht.data.model.RecipeResponse
import com.example.cookbookht.data.model.SearchRecipeResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface APIService {

    @GET("random")
    suspend fun getRecipe(@Query("number") number: Int): RecipeResponse

    //https://api.spoonacular.com/recipes/716429/information?apiKey=cf90ee8d96ad45a68cb4c292a2aabdfa&includeNutrition=true
    @GET("{id}/information")
    suspend fun getRecipeDetail(
        @Path("id") id: Int?,
        @Query("includeNutrition") include: Boolean = true
    ): RecipeDetail

    //api.spoonacular.com/recipes/complexSearch?query=pasta&number=2&apiKey=cf90ee8d96ad45a68cb4c292a2aabdfa
    @GET("complexSearch")
    suspend fun searchRecipe(
        @Query("query") keyword: String,
        @Query("number") number: Int,
    ): SearchRecipeResponse
}
