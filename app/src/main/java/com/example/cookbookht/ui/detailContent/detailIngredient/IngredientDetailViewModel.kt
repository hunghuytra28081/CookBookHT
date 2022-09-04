package com.example.cookbookht.ui.detailContent.detailIngredient

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cookbookht.data.model.Ingredient
import com.example.cookbookht.data.model.Recipe
import com.example.cookbookht.data.repository.RecipeRepository
import com.example.cookbookht.extension.plusAssign
import com.example.cookbookht.utils.Resource
import kotlinx.coroutines.launch

class IngredientDetailViewModel(
    private var recipeRepository: RecipeRepository
): ViewModel() {

    private val _ingredientDetailLiveData = MutableLiveData<Resource<LiveData<MutableList<Ingredient>>>>()
    val ingredientDetailLiveData: MutableLiveData<Resource<LiveData<MutableList<Ingredient>>>>
        get() = _ingredientDetailLiveData

    private val _ingredient = MutableLiveData<MutableList<Ingredient>>()
    val ingredient: MutableLiveData<MutableList<Ingredient>>
        get() = _ingredient

    fun fetchIngredientDetail(id: Int?) {
        viewModelScope.launch {
            _ingredientDetailLiveData.postValue(Resource.loading(null))
            try {
                recipeRepository.getRecipeDetail(id).let {
                    _ingredient.plusAssign(it.ingredient)
                }
                _ingredientDetailLiveData.postValue(
                    Resource.success(data = ingredient)
                )
            } catch (e: Exception) {
                _ingredientDetailLiveData.postValue(Resource.error(null, e.localizedMessage))
            }
        }
    }
}