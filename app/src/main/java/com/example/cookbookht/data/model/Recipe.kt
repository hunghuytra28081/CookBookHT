package com.example.cookbookht.data.model

import android.os.Parcelable
import androidx.recyclerview.widget.DiffUtil
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Recipe(
    @SerializedName("id")
    @Expose
    val id: Int? = 0,
    @SerializedName("title")
    @Expose
    val title: String? = null,
    @SerializedName("image")
    @Expose
    val image: String? = null
) : Parcelable{

    companion object {

        val diffUtil = object : DiffUtil.ItemCallback<Recipe>() {
            override fun areItemsTheSame(oldItem: Recipe, newItem: Recipe) =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Recipe, newItem: Recipe): Boolean =
                oldItem == newItem
        }
    }
}
