package com.example.cookbookht.data.model

import com.google.gson.annotations.SerializedName

data class SearchRecipeResponse(
    @SerializedName("results")
    val recipes: MutableList<Recipe>
)
