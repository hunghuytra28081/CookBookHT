package com.example.cookbookht.data.model

import android.os.Parcelable
import androidx.recyclerview.widget.DiffUtil
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Ingredient(
    @SerializedName("id")
    @Expose
    val id: Int?,
    @SerializedName("name")
    @Expose
    val name: String?,
    @SerializedName("image")
    @Expose
    val image: String?,
    @SerializedName("amount")
    @Expose
    val amount: Double?,
    @SerializedName("unit")
    @Expose
    val unit: String?
) : Parcelable {

    companion object {

        val diffUtil = object : DiffUtil.ItemCallback<Ingredient>() {
            override fun areItemsTheSame(oldItem: Ingredient, newItem: Ingredient) =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Ingredient, newItem: Ingredient): Boolean =
                oldItem == newItem
        }
    }
}
