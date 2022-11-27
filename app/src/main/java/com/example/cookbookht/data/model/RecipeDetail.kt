package com.example.cookbookht.data.model

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

@Parcelize
data class RecipeDetail(
    @SerializedName("id")
    @Expose
    val id: Int,
    @SerializedName("title")
    @Expose
    val title: String?,
    @SerializedName("image")
    @Expose
    val image: String?,
    @SerializedName("readyInMinutes")
    @Expose
    val readyInMinutes: Int?,
    @SerializedName("aggregateLikes")
    @Expose
    val like: Int?,
    @SerializedName("summary")
    @Expose
    var summary: String?,
    @SerializedName("extendedIngredients")
    @Expose
    val ingredient: MutableList<Ingredient>,
    @SerializedName("nutrition")
    @Expose
    val nutrients: @RawValue NutrientResponse,
    @SerializedName("analyzedInstructions")
    @Expose
    val instructions: @RawValue MutableList<InstructionResponse>
): Parcelable
