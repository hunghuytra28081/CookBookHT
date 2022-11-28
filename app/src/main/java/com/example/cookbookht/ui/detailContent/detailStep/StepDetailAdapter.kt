package com.example.cookbookht.ui.detailContent.detailStep

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.cookbookht.data.model.Nutrient
import com.example.cookbookht.data.model.Step
import com.example.cookbookht.data.repository.source.remote.api.translate.RetrofitBuilder
import com.example.cookbookht.databinding.ItemStepBinding
import com.example.cookbookht.sharePreference.Preferences
import com.example.cookbookht.utils.BindingDataRecyclerView
import com.example.cookbookht.utils.Constant.API_KEY_TRANSLATE
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.await

class StepDetailAdapter(
    private val onItemClick: (Step) -> Unit,
    private val prefs: Preferences
) : ListAdapter<Step, StepViewHolder>(Step.diffUtil),
    BindingDataRecyclerView<MutableList<Step>> {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StepViewHolder {
        val binding = ItemStepBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return StepViewHolder(binding, onItemClick, prefs)
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
    private val onItemClick: (Step) -> Unit,
    private val prefs: Preferences
) : RecyclerView.ViewHolder(binding.root) {

    fun onBind(step: Step) {
        CoroutineScope(Dispatchers.Main).launch {
            binding.apply {
                this.step = step
                when (prefs.isLanguageVi.get()) {
                    "vi" -> {
                        try {
                            val response = RetrofitBuilder.apiService.getDataTranslate(
                                step.step ?: "",
                                API_KEY_TRANSLATE
                            ).await()
                            binding.step?.step =
                                response.data.translations.joinToString { it.translatedText }
                            binding.step = binding.step
                        } catch (e: Exception) {
                            e.printStackTrace()
                            Log.d("Main12345", "Error: $e")
                        }
                    }
                    else -> {}
                }
                executePendingBindings()
                root.setOnClickListener {
                    onItemClick(step)
                }
            }
        }
    }
}