package com.example.cookbookht.data.model

import android.os.Parcelable
import androidx.recyclerview.widget.DiffUtil
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Step(
    @SerializedName("number")
    @Expose
    val number: Int?,
    @SerializedName("step")
    @Expose
    var step: String?
) : Parcelable {

    companion object {

        val diffUtil = object : DiffUtil.ItemCallback<Step>() {
            override fun areItemsTheSame(oldItem: Step, newItem: Step) =
                oldItem.number == newItem.number

            override fun areContentsTheSame(oldItem: Step, newItem: Step): Boolean =
                oldItem == newItem
        }
    }
}
