package com.example.cookbookht.ui.search

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cookbookht.data.model.Recipe
import com.example.cookbookht.data.repository.RecipeRepository
import com.example.cookbookht.data.repository.source.local.History
import com.example.cookbookht.extension.plusAssign
import com.example.cookbookht.utils.Constant
import com.example.cookbookht.utils.LoadMoreRecyclerViewListener
import com.example.cookbookht.utils.RefreshRecyclerViewListener
import com.example.cookbookht.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SearchViewModel(
    private val repository: RecipeRepository
) : ViewModel(),
    LoadMoreRecyclerViewListener,
    RefreshRecyclerViewListener {

    private var currentNumber: Int = Constant.DEFAULT_NUMBER_RECIPE
    private var keyword: String = Constant.DEFAULT_STRING

    private val _recipes = MutableLiveData<MutableList<Recipe>>()
    val recipes: MutableLiveData<MutableList<Recipe>>
        get() = _recipes

    private val _resource = MutableLiveData<Resource<MutableLiveData<MutableList<Recipe>>>>()
    val resource: MutableLiveData<Resource<MutableLiveData<MutableList<Recipe>>>>
        get() = _resource

    private val _history = MutableLiveData<Resource<List<History>>>()
    val history: MutableLiveData<Resource<List<History>>>
        get() = _history

    private val _isLoad = MutableLiveData(false)
    val isLoad: MutableLiveData<Boolean>
        get() = _isLoad

    override fun onLoadData() {
        _isLoad.value = true
        Handler(Looper.getMainLooper()).postDelayed({
            searchRecipe(keyword)
        }, Constant.DELAY_TIME)
    }

    override fun onRefresh() {
        _recipes.value?.clear()
        searchRecipe(keyword)
    }

    fun querySearch(keyword: String) {
        _recipes.value?.clear()
        searchRecipe(keyword)
    }

    fun onRefreshSearch(keyword: String) {
        _recipes.value?.clear()
        searchRecipe(keyword)
    }

    fun searchRecipe(keyword: String = Constant.DEFAULT_STRING) {
        viewModelScope.launch {
            try {
                _recipes.plusAssign(
                    repository.searchRecipe(keyword, currentNumber).recipes
                )
                _resource.postValue(Resource.success(data = recipes))
                _isLoad.value = false
            } catch (e: Exception) {
                _resource.postValue(Resource.error(data = null, message = e.localizedMessage))
            }
        }
    }

    fun getAllHistory() {
        viewModelScope.launch {
            _history.postValue(Resource.loading(null))
            try {
                _history.postValue(Resource.success(data = repository.getAllHistory()))
            } catch (e: Exception) {
                _history.postValue(Resource.error(null, e.stackTraceToString()))
            }
        }
    }

    fun insertHistory(history: History) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                repository.insertHistory(history)
            } catch (ex: java.lang.Exception) {
                _history.postValue(Resource.error(null, ex.toString()))
            }
        }
    }

    fun deleteHistory(name: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                repository.deleteHistory(name)
            } catch (ex: java.lang.Exception) {
                _history.postValue(Resource.error(null, ex.toString()))
            }
        }
    }

    fun checkIsExistHistory(name: String): Boolean {
        return repository.isExistHistory(name) == null
    }
}