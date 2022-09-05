package com.example.cookbookht.data.model

import android.os.Parcelable
import androidx.recyclerview.widget.DiffUtil
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
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
) : Parcelable {

    companion object {

        val diffUtil = object : DiffUtil.ItemCallback<Nutrient>() {
            override fun areItemsTheSame(oldItem: Nutrient, newItem: Nutrient) =
                oldItem.name == newItem.name

            override fun areContentsTheSame(oldItem: Nutrient, newItem: Nutrient): Boolean =
                oldItem == newItem
        }
    }
}
