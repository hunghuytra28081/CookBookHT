package com.example.cookbookht.ui.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cookbookht.data.repository.RecipeRepository
import com.example.cookbookht.data.repository.source.local.entities.Favorite
import com.example.cookbookht.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

class FavoriteViewModel(
    private var repository: RecipeRepository
) : ViewModel() {

    private val _favoriteLiveData = MutableLiveData<Resource<List<Favorite>>>()
    val favoriteLiveData: LiveData<Resource<List<Favorite>>>
        get() = _favoriteLiveData

    fun getFavorite() {
        viewModelScope.launch(Dispatchers.IO) {
            _favoriteLiveData.postValue(Resource.loading(null))
            try {
                _favoriteLiveData.postValue(Resource.success(repository.getAllFavorite()))
            } catch (ex: Exception) {
                _favoriteLiveData.postValue(Resource.error(null, ex.toString()))
            }
        }
    }
}
