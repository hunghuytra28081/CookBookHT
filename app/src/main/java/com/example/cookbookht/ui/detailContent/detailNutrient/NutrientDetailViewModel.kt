package com.example.cookbookht.ui.detailContent.detailNutrient

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cookbookht.data.model.Nutrient
import com.example.cookbookht.data.repository.RecipeRepository
import com.example.cookbookht.extension.plusAssign
import com.example.cookbookht.utils.Resource
import kotlinx.coroutines.launch

class NutrientDetailViewModel(
    private var recipeRepository: RecipeRepository
) : ViewModel() {

    private val _nutrientDetailLiveData =
        MutableLiveData<Resource<LiveData<MutableList<Nutrient>>>>()
    val nutrientDetailLiveData: MutableLiveData<Resource<LiveData<MutableList<Nutrient>>>>
        get() = _nutrientDetailLiveData

    private val _nutrient = MutableLiveData<MutableList<Nutrient>>()
    val nutrient: MutableLiveData<MutableList<Nutrient>>
        get() = _nutrient

    fun fetchNutrientDetail(id: Int?){
        viewModelScope.launch {
            _nutrientDetailLiveData.postValue(Resource.loading(null))
            try {
                recipeRepository.getRecipeDetail(id).nutrients.let {
                    _nutrient.plusAssign(it.nutrients)
                }
                _nutrientDetailLiveData.postValue(
                    Resource.success(data = nutrient)
                )
            } catch (e: Exception) {
                _nutrientDetailLiveData.postValue(Resource.error(null, e.localizedMessage))
            }
        }
    }
}