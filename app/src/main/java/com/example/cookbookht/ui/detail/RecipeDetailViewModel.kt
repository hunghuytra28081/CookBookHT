package com.example.cookbookht.ui.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cookbookht.data.model.RecipeDetail
import com.example.cookbookht.data.repository.RecipeRepository
import com.example.cookbookht.utils.Resource
import kotlinx.coroutines.launch

class RecipeDetailViewModel(
    private var recipeRepository: RecipeRepository
) : ViewModel() {

    private val _recipeDetailLiveData = MutableLiveData<Resource<RecipeDetail>>()
    val recipeDetailLiveData: MutableLiveData<Resource<RecipeDetail>>
        get() = _recipeDetailLiveData

    fun fetchRecipeDetail(id: Int?) {
        viewModelScope.launch {
            _recipeDetailLiveData.postValue(Resource.loading(null))
            try {
                _recipeDetailLiveData.postValue(
                    Resource.success(recipeRepository.getRecipeDetail(id))
                )
            } catch (e: Exception) {
                _recipeDetailLiveData.postValue(Resource.error(null, e.localizedMessage))
            }
        }
    }
}