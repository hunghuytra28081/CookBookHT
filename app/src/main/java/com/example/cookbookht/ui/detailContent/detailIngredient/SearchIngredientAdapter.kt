package com.example.cookbookht.ui.detailContent.detailIngredient

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.cookbookht.data.model.Recipe
import com.example.cookbookht.databinding.ItemLoadMoreBinding
import com.example.cookbookht.databinding.ItemRecipeHomeBinding
import com.example.cookbookht.ui.home.HomeAdapter
import com.example.cookbookht.utils.BindingDataRecyclerView

class SearchIngredientAdapter(
    private val onItemClick: (Recipe) -> Unit
) : ListAdapter<Recipe, RecyclerView.ViewHolder>(Recipe.diffUtil),
    BindingDataRecyclerView<MutableList<Recipe>> {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == HomeAdapter.VIEW_TYPE_LOADING) {
            val binding = ItemLoadMoreBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            LoadItemViewHolder(binding)
        } else {
            val binding = ItemRecipeHomeBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            SearchIngredientViewHolder(binding, onItemClick)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is SearchIngredientViewHolder) {
            holder.onBind(getItem(position))
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun setData(data: MutableList<Recipe>?) {
        data?.let {
            if (it == currentList) {
                notifyDataSetChanged()
            } else {
                submitList(it)
            }
        }
    }

    class LoadItemViewHolder(binding: ItemLoadMoreBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun getItemViewType(position: Int): Int {
        return if (getItem(position).id == null) VIEW_TYPE_LOADING else VIEW_TYPE_ITEM
    }

    class SearchIngredientViewHolder(
        private val binding: ItemRecipeHomeBinding,
        val onItemClick: (Recipe) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun onBind(recipe: Recipe) {
            binding.apply {
                this.recipe = recipe
                executePendingBindings()
                root.setOnClickListener {
                    onItemClick(recipe)
                }
            }
        }
    }

    companion object {
        const val VIEW_TYPE_LOADING = 0
        const val VIEW_TYPE_ITEM = 1
    }
}