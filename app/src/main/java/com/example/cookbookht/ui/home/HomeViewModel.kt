package com.example.cookbookht.ui.home

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cookbookht.data.model.Recipe
import com.example.cookbookht.data.repository.RecipeRepository
import com.example.cookbookht.extension.plusAssign
import com.example.cookbookht.utils.Constant
import com.example.cookbookht.utils.Constant.DEFAULT_NUMBER_RECIPE
import com.example.cookbookht.utils.LoadMoreRecyclerViewListener
import com.example.cookbookht.utils.RefreshRecyclerViewListener
import com.example.cookbookht.utils.Resource
import kotlinx.coroutines.launch

class HomeViewModel(private val recipeRepository: RecipeRepository) : ViewModel(),
    LoadMoreRecyclerViewListener,
    RefreshRecyclerViewListener {

    private var currentNumber: Int = DEFAULT_NUMBER_RECIPE

    private val _recipes = MutableLiveData<MutableList<Recipe>>()
    val recipe: MutableLiveData<MutableList<Recipe>>
        get() = _recipes

    private val _resource = MutableLiveData<Resource<LiveData<MutableList<Recipe>>>>()
    val resource: LiveData<Resource<LiveData<MutableList<Recipe>>>>
        get() = _resource

    private val _isLoad = MutableLiveData<Boolean>(false)
    val isLoad: MutableLiveData<Boolean>
        get() = _isLoad

    init {
        fetchRecipe()
    }

    private fun fetchRecipe() {
        viewModelScope.launch {
            try {
                recipeRepository.getRecipe(currentNumber).let {
                    _recipes.plusAssign(it.recipe)
                }
                _resource.postValue(Resource.success(data = recipe))
            } catch (e: Exception) {
                _resource.postValue(Resource.error(data = null, message = e.stackTraceToString()))
            }
        }
    }

    override fun onLoadData() {
        _isLoad.value = true
        addLoading()
        Handler(Looper.getMainLooper()).postDelayed({
            removeItemLoading()
            currentNumber += DEFAULT_NUMBER_RECIPE
            recipe.value?.let {
                fetchRecipe()
            }
            _isLoad.value = false
        }, Constant.DELAY_TIME)
    }

    private fun addItemLoad(recipe: MutableList<Recipe>) {
        recipe.add(Recipe(null, "", ""))
    }

    private fun removeItemLoading() {
        if (_isLoad.value == true) {
            recipe.value?.let {
                it.removeAt(it.size - 1)
            }
        }
    }

    private fun addLoading() {
        _recipes.plusAssign(Recipe())
    }

    override fun onRefresh() {
        _recipes.value?.clear()
        currentNumber = DEFAULT_NUMBER_RECIPE
        fetchRecipe()
    }
}
