package com.example.cookbookht.ui.detailContent.detailStep

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.cookbookht.data.model.Nutrient
import com.example.cookbookht.data.model.Step
import com.example.cookbookht.databinding.ItemStepBinding
import com.example.cookbookht.utils.BindingDataRecyclerView

class StepDetailAdapter(
    private val onItemClick: (Step) -> Unit
) : ListAdapter<Step, StepViewHolder>(Step.diffUtil),
    BindingDataRecyclerView<MutableList<Step>> {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StepViewHolder {
        val binding = ItemStepBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return StepViewHolder(binding, onItemClick)
    }

    override fun onBindViewHolder(holder: StepViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }

    override fun setData(data: MutableList<Step>?) {
        data.let {
            if (it == currentList) {
                notifyDataSetChanged()
            } else {
                submitList(it)
            }
        }
    }
}

class StepViewHolder(
    private val binding: ItemStepBinding,
    private val onItemClick: (Step) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun onBind(step: Step) {
        binding.apply {
            this.step = step
            executePendingBindings()
            root.setOnClickListener {
                onItemClick(step)
            }
        }
    }
}