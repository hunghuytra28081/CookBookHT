package com.example.cookbookht.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Nutrient(
    @SerializedName("name")
    @Expose
    val name: String?,
    @SerializedName("amount")
    @Expose
    val amount: Double?,
    @SerializedName("unit")
    @Expose
    val unit: String?
)
