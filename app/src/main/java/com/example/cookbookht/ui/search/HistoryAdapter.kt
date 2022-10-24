package com.example.cookbookht.ui.search

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.cookbookht.R
import com.example.cookbookht.data.repository.source.local.History
import com.example.cookbookht.databinding.ItemHistoryBinding
import com.example.cookbookht.utils.BindingDataRecyclerView

class HistoryAdapter(
    private val context: Context,
    private val onItemClick: (History) -> Unit
) :
    ListAdapter<History, RecyclerView.ViewHolder>(
        HistoryDiffUtilCallBack()
    ), BindingDataRecyclerView<MutableList<History>> {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = ItemHistoryBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return HistoryViewHolder(context, binding, onItemClick)
    }

    class HistoryDiffUtilCallBack : DiffUtil.ItemCallback<History>() {

        override fun areItemsTheSame(oldItem: History, newItem: History): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: History, newItem: History): Boolean {
            return oldItem == newItem
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is HistoryViewHolder) {
            holder.onBind(getItem(position))
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun setData(data: MutableList<History>?) {
        data?.let {
            if (it == currentList) {
                notifyDataSetChanged()
            } else {
                submitList(it)
            }
        }
    }

    class HistoryViewHolder(
        context: Context,
        private val binding: ItemHistoryBinding,
        val onItemClick: (History) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        private val randomColor = context.resources.getStringArray(R.array.random_colors)

        fun onBind(history: History) {
            binding.apply {
                this.history = history
                layoutHistory.setBackgroundColor(Color.parseColor(randomColor.random()))
                executePendingBindings()
                root.setOnClickListener {
                    onItemClick(history)
                }
            }
        }
    }
}
