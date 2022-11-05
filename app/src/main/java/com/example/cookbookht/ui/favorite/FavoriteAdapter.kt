package com.example.cookbookht.ui.favorite

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.cookbookht.data.repository.source.local.entities.Favorite
import com.example.cookbookht.databinding.ItemFavoriteBinding
import com.example.cookbookht.utils.BindingDataRecyclerView

class FavoriteAdapter(private val onItemClick: (Favorite) -> Unit) :
    ListAdapter<Favorite, RecyclerView.ViewHolder>(FavoriteDiffUtilCallBack()){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = ItemFavoriteBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return FavoriteViewHolder(binding, onItemClick)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is FavoriteViewHolder) {
            holder.onBind(getItem(position))
        }
    }

    class FavoriteViewHolder(
        private val binding: ItemFavoriteBinding,
        val onItemClick: (Favorite) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun onBind(favorite: Favorite) {
            binding.apply {
                this.favorite = favorite
                executePendingBindings()
                root.setOnClickListener {
                    onItemClick(favorite)
                }
            }
        }
    }


    class FavoriteDiffUtilCallBack : DiffUtil.ItemCallback<Favorite>() {

        override fun areItemsTheSame(oldItem: Favorite, newItem: Favorite): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Favorite, newItem: Favorite): Boolean {
            return oldItem == newItem
        }
    }
}