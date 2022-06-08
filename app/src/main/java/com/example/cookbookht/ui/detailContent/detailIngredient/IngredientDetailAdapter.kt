package com.example.cookbookht.ui.detailContent.detailIngredient

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.cookbookht.binding.loadImage
import com.example.cookbookht.data.model.Ingredient
import com.example.cookbookht.databinding.ItemIngredientBinding
import com.example.cookbookht.utils.BindingDataRecyclerView
import com.example.cookbookht.utils.Constant

class IngredientDetailAdapter(
    private val onItemClick: (Ingredient) -> Unit
) : ListAdapter<Ingredient, IngredientDetailViewHolder>(Ingredient.diffUtil),
    BindingDataRecyclerView<MutableList<Ingredient>> {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientDetailViewHolder {
        val binding = ItemIngredientBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return IngredientDetailViewHolder(binding, onItemClick)
    }

    override fun onBindViewHolder(holder: IngredientDetailViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun setData(data: MutableList<Ingredient>?) {
        data.let {
            if (it == currentList) {
                notifyDataSetChanged()
            } else {
                submitList(it)
            }
        }
    }
}

class IngredientDetailViewHolder(
    private val binding: ItemIngredientBinding,
    private val onItemClick: (Ingredient) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun onBind(ingredient: Ingredient) {
        binding.apply {
            this.ingredient = ingredient
            imageIngredient.loadImage(Constant.BASE_URL_IMAGE_INGREDIENT + ingredient.image)
            executePendingBindings()
            root.setOnClickListener {
                onItemClick(ingredient)
            }
        }
    }
}
