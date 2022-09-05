package com.example.cookbookht.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class NutrientResponse(
    @SerializedName("nutrients")
    @Expose
    val nutrients: MutableList<Nutrient>
)
