package com.example.cookbookht.ui.detailContent.detailIngredient

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cookbookht.data.model.Ingredient
import com.example.cookbookht.data.model.Recipe
import com.example.cookbookht.data.repository.RecipeRepository
import com.example.cookbookht.extension.plusAssign
import com.example.cookbookht.utils.Constant
import com.example.cookbookht.utils.Constant.DEFAULT_NUMBER_RECIPE
import com.example.cookbookht.utils.LoadMoreRecyclerViewListener
import com.example.cookbookht.utils.Resource
import kotlinx.coroutines.launch

class IngredientDetailViewModel(
    private var recipeRepository: RecipeRepository
) : ViewModel(), LoadMoreRecyclerViewListener {

    private var currentNumber: Int = Constant.DEFAULT_NUMBER_RECIPE

    private val _ingredientDetailLiveData =
        MutableLiveData<Resource<LiveData<MutableList<Ingredient>>>>()
    val ingredientDetailLiveData: MutableLiveData<Resource<LiveData<MutableList<Ingredient>>>>
        get() = _ingredientDetailLiveData

    private val _ingredient = MutableLiveData<MutableList<Ingredient>>()
    val ingredient: MutableLiveData<MutableList<Ingredient>>
        get() = _ingredient

    private val _searchIngredient = MutableLiveData<MutableList<Recipe>>()
    val searchIngredient: MutableLiveData<MutableList<Recipe>>
        get() = _searchIngredient

    private val _resource = MutableLiveData<Resource<LiveData<MutableList<Recipe>>>>()
    val resource: LiveData<Resource<LiveData<MutableList<Recipe>>>>
        get() = _resource

    private val _isLoad = MutableLiveData<Boolean>(false)
    val isLoad: MutableLiveData<Boolean>
        get() = _isLoad

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

    fun fetchSearchIngredient(keyword: String = Constant.DEFAULT_STRING) {
        viewModelScope.launch {
            try {
                recipeRepository.searchIngredient(keyword, currentNumber).let { recipe ->
                    if (recipe.size == 0) {
                        removeItemLoading()
                        _isLoad.value = true
                        return@launch
                    } else if (recipe.size >= currentNumber) {
                        addItemLoad(recipe)
                    }
                    _searchIngredient.plusAssign(recipe)
                    currentNumber += DEFAULT_NUMBER_RECIPE
                    _isLoad.value = false
                }
                _resource.postValue(Resource.success(data = searchIngredient))
            } catch (e: Exception) {
                _resource.postValue(Resource.error(data = null, message = e.stackTraceToString()))
            }
        }
    }

    override fun onLoadData() {
        _isLoad.value = true
//        addLoading()
        Handler(Looper.getMainLooper()).postDelayed({
            _isLoad.value = false
//            currentNumber += DEFAULT_NUMBER_RECIPE
            removeItemLoading()
            fetchSearchIngredient()
        }, Constant.DELAY_TIME)
    }

    private fun addItemLoad(recipe: MutableList<Recipe>) {
        recipe.add(Recipe(null, "", ""))
    }

    private fun removeItemLoading() {
        _searchIngredient.apply { value?.size?.let { value?.removeAt(it - 1) } }
    }

    private fun addLoading() {
        _searchIngredient.plusAssign(Recipe())
    }

    fun onClearData() {
        _searchIngredient.value?.clear()
    }
}