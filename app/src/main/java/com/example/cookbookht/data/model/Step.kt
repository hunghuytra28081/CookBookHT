package com.example.cookbookht.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Step(
    @SerializedName("number")
    @Expose
    val number: Int?,
    @SerializedName("step")
    @Expose
    val step: String?
)
