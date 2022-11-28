package com.example.cookbookht.ui.detailContent.detailNutrient

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.cookbookht.data.model.Nutrient
import com.example.cookbookht.data.repository.source.remote.api.translate.RetrofitBuilder
import com.example.cookbookht.databinding.ItemNutrientBinding
import com.example.cookbookht.sharePreference.Preferences
import com.example.cookbookht.utils.BindingDataRecyclerView
import com.example.cookbookht.utils.Constant
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.await

class NutrientDetailAdapter(
    private val onItemClick: (Nutrient) -> Unit,
    private val prefs: Preferences
) : ListAdapter<Nutrient, NutrientDetailViewHolder>(Nutrient.diffUtil),
    BindingDataRecyclerView<MutableList<Nutrient>> {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NutrientDetailViewHolder {
        val binding = ItemNutrientBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return NutrientDetailViewHolder(binding, onItemClick, prefs)
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
    private val onItemClick: (Nutrient) -> Unit,
    private val prefs: Preferences
) : RecyclerView.ViewHolder(binding.root) {

    fun onBind(nutrient: Nutrient) {
        binding.apply {
            this.nutrient = nutrient
            executePendingBindings()
            root.setOnClickListener {
                onItemClick(nutrient)
            }
        }

        CoroutineScope(Dispatchers.Main).launch {
            when (prefs.isLanguageVi.get()) {
                "vi" -> {
                    try {
                        val response = RetrofitBuilder.apiService.getDataTranslate(
                            nutrient.name ?: "",
                            Constant.API_KEY_TRANSLATE
                        ).await()
                        Log.e("Main12345", "Response: ${Gson().toJson(response)}")
                        binding.nutrient?.name =
                            response.data.translations.joinToString { it.translatedText }
                        binding.nutrient = binding.nutrient
                    } catch (e: Exception) {
                        e.printStackTrace()
                        Log.d("Main12345", "Error: $e")
                    }
                }
                else -> {}
            }
        }
    }
}
