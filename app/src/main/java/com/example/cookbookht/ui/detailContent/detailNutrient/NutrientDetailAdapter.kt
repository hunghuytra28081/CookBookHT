package com.example.cookbookht.ui.detailContent.detailNutrient

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.cookbookht.data.model.Nutrient
import com.example.cookbookht.databinding.ItemNutrientBinding
import com.example.cookbookht.utils.BindingDataRecyclerView

class NutrientDetailAdapter(
    private val onItemClick: (Nutrient) -> Unit
) : ListAdapter<Nutrient, NutrientDetailViewHolder>(Nutrient.diffUtil),
    BindingDataRecyclerView<MutableList<Nutrient>> {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NutrientDetailViewHolder {
        val binding = ItemNutrientBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return NutrientDetailViewHolder(binding, onItemClick)
    }

    override fun onBindViewHolder(holder: NutrientDetailViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }

    override fun setData(data: MutableList<Nutrient>?) {
        data.let {
            if (it == currentList) {
                notifyDataSetChanged()
            } else {
                submitList(it)
            }
        }
    }
}

class NutrientDetailViewHolder(
    private val binding: ItemNutrientBinding,
    private val onItemClick: (Nutrient) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun onBind(nutrient: Nutrient) {
        binding.apply {
            this.nutrient = nutrient
            executePendingBindings()
            root.setOnClickListener {
                onItemClick(nutrient)
            }
        }
    }
}
