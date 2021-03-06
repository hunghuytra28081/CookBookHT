package com.example.cookbookht.utils

import com.example.cookbookht.BuildConfig

object Constant {
    const val API_KEY = BuildConfig.API_KEY
    const val API_KEY_WORD = "apiKey"
    const val BASE_URL = "https://api.spoonacular.com/recipes/"
    const val DEFAULT_NUMBER_RECIPE = 20
    const val DEFAULT_STRING = ""
    const val BASE_URL_IMAGE = "https://spoonacular.com/recipeImages/"
    const val IMAGE_RECIPE_SIZE = "-556x370.jpg"
    const val BASE_URL_IMAGE_INGREDIENT = "https://spoonacular.com/cdn/ingredients_100x100/"
    const val DELAY_TIME = 2000L
}