package com.example.cookbookht.ui.home


import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.cookbookht.data.model.Recipe
import com.example.cookbookht.databinding.ItemLoadMoreBinding
import com.example.cookbookht.databinding.ItemRecipeHomeBinding
import com.example.cookbookht.extension.translateToVi
import com.example.cookbookht.utils.BindingDataRecyclerView
import kotlinx.coroutines.CoroutineScope

class HomeAdapter(
    private val onItemClick: (Recipe) -> Unit
) :
    ListAdapter<Recipe, RecyclerView.ViewHolder>(RecipeDiffUtilCallBack()),
    BindingDataRecyclerView<MutableList<Recipe>> {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == VIEW_TYPE_LOADING) {
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
            RecipeViewHolder(binding, onItemClick)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (getItem(position).id == null) VIEW_TYPE_LOADING else VIEW_TYPE_ITEM
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is RecipeViewHolder) {
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

    class RecipeViewHolder(
        private val binding: ItemRecipeHomeBinding,
        val onItemClick: (Recipe) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun onBind(recipe: Recipe) {
            binding.apply {
                this.recipe = recipe
//                titleHomeTextView.text = scope.translateToVi(recipe.title)
//                titleHomeTextView.text = recipe.title?.translateToVi()
                executePendingBindings()
                root.setOnClickListener {
                    onItemClick(recipe)
                }
            }
        }
    }

    class LoadItemViewHolder(binding: ItemLoadMoreBinding) :
        RecyclerView.ViewHolder(binding.root)

    class RecipeDiffUtilCallBack : DiffUtil.ItemCallback<Recipe>() {

        override fun areItemsTheSame(oldItem: Recipe, newItem: Recipe): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Recipe, newItem: Recipe): Boolean {
            return oldItem == newItem
        }
    }

    companion object {

        const val VIEW_TYPE_LOADING = 0
        const val VIEW_TYPE_ITEM = 1
    }
}
