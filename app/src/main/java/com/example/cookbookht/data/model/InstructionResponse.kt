package com.example.cookbookht.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class InstructionResponse(
    @SerializedName("name")
    @Expose
    val name: String?,
    @SerializedName("steps")
    @Expose
    val steps: MutableList<Step>?
)
