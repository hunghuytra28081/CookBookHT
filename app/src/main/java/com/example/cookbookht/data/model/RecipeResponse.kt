package com.example.cookbookht.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class RecipeResponse(
    @SerializedName("recipes")
    @Expose
    val recipe: MutableList<Recipe>
)
