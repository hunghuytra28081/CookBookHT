package com.example.cookbookht.ui.detailContent.detailIngredient

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cookbookht.data.model.Ingredient
import com.example.cookbookht.data.repository.RecipeRepository
import com.example.cookbookht.utils.Resource
import kotlinx.coroutines.launch

class IngredientDetailViewModel(
    private var recipeRepository: RecipeRepository
): ViewModel() {

    private val _ingredientDetailLiveData = MutableLiveData<Resource<MutableList<Ingredient>?>>()
    val ingredientDetailLiveData: MutableLiveData<Resource<MutableList<Ingredient>?>>
        get() = _ingredientDetailLiveData

    fun fetchIngredientDetail(id: Int?) {
        viewModelScope.launch {
            _ingredientDetailLiveData.postValue(Resource.loading(null))
            try {
                _ingredientDetailLiveData.postValue(
                    Resource.success(recipeRepository.getRecipeDetail(id).ingredient)
                )
            } catch (e: Exception) {
                _ingredientDetailLiveData.postValue(Resource.error(null, e.localizedMessage))
            }
        }
    }
}