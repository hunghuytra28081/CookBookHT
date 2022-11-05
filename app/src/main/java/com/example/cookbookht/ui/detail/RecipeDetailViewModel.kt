package com.example.cookbookht.ui.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cookbookht.data.model.RecipeDetail
import com.example.cookbookht.data.repository.RecipeRepository
import com.example.cookbookht.data.repository.source.local.entities.Favorite
import com.example.cookbookht.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RecipeDetailViewModel(
    private var recipeRepository: RecipeRepository
) : ViewModel() {

    private val _recipeDetailLiveData = MutableLiveData<Resource<RecipeDetail>>()
    val recipeDetailLiveData: MutableLiveData<Resource<RecipeDetail>>
        get() = _recipeDetailLiveData

    private val _isFavorite = MutableLiveData<Resource<Boolean>>()
    val isFavorite: MutableLiveData<Resource<Boolean>>
        get() = _isFavorite

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

    fun insertFavorite(favorite: Favorite) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                recipeRepository.insertFavorite(favorite)
                _isFavorite.postValue(Resource.success(true))
            } catch (ex: java.lang.Exception) {
                _isFavorite.postValue(Resource.error(null, ex.toString()))
            }
        }
    }

    fun deleteFavorite(favorite: Favorite) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                recipeRepository.deleteFavorite(favorite)
                _isFavorite.postValue(Resource.success(false))
            } catch (ex: java.lang.Exception) {
                _isFavorite.postValue(Resource.error(null, ex.toString()))
            }
        }
    }

    fun alreadyFavorite(id: Int?) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                if (recipeRepository.alreadyFavorite(id).image.isNullOrEmpty()) {
                    _isFavorite.postValue(Resource.success(false))
                } else
                    _isFavorite.postValue(Resource.success(true))
            } catch (ex: java.lang.Exception) {
                _isFavorite.postValue(Resource.error(null, ex.toString()))
            }
        }
    }
}