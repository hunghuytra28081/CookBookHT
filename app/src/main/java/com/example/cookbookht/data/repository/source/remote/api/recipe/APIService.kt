package com.example.cookbookht.data.repository.source.remote.api.recipe

import com.example.cookbookht.data.model.Recipe
import com.example.cookbookht.data.model.RecipeDetail
import com.example.cookbookht.data.model.RecipeResponse
import com.example.cookbookht.data.model.SearchRecipeResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface APIService {

    //api.spoonacular.com/recipes/random?number=20&apiKey=cf90ee8d96ad45a68cb4c292a2aabdfa
    @GET("random")
    suspend fun getRecipe(@Query("number") number: Int): RecipeResponse

    //api.spoonacular.com/recipes/716429/information?apiKey=cf90ee8d96ad45a68cb4c292a2aabdfa&includeNutrition=true
    @GET("{id}/information")
    suspend fun getRecipeDetail(
        @Path("id") id: Int?,
        @Query("includeNutrition") include: Boolean = true
    ): RecipeDetail

    //api.spoonacular.com/recipes/complexSearch?query=drink&offset=2&number=20&apiKey=cf90ee8d96ad45a68cb4c292a2aabdfa
    @GET("complexSearch")
    suspend fun searchRecipe(
        @Query("query") keyword: String,
        @Query("offset") offset: Int,
        @Query("number") number: Int = 20
    ): SearchRecipeResponse

    //https://api.spoonacular.com/recipes/findByIngredients?ingredients=apples,+flour,+sugar&number=2&apiKey=cf90ee8d96ad45a68cb4c292a2aabdfa
    @GET("findByIngredients")
    suspend fun searchIngredient(
        @Query("ingredients") keyword: String,
        @Query("number") number: Int,
    ): MutableList<Recipe>
}
