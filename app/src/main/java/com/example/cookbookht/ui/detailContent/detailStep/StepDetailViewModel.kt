package com.example.cookbookht.ui.detailContent.detailStep

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cookbookht.data.model.Step
import com.example.cookbookht.data.repository.RecipeRepository
import com.example.cookbookht.extension.plusAssign
import com.example.cookbookht.utils.Resource
import kotlinx.coroutines.launch

class StepDetailViewModel(
    private val recipeRepository: RecipeRepository
) : ViewModel() {

    private val _stepDetailLiveData =
        MutableLiveData<Resource<LiveData<MutableList<Step>>>>()
    val stepDetailLiveData: MutableLiveData<Resource<LiveData<MutableList<Step>>>>
        get() = _stepDetailLiveData

    private val _step = MutableLiveData<MutableList<Step>>()
    val step: MutableLiveData<MutableList<Step>>
        get() = _step

    fun fetchStepDetail(id: Int?){
        viewModelScope.launch {
            _stepDetailLiveData.postValue(Resource.loading(null))
            try {
                recipeRepository.getRecipeDetail(id).instructions[0].let {
                    _step.plusAssign(it.steps)
                }
                _stepDetailLiveData.postValue(
                    Resource.success(data = step)
                )
            } catch (e: Exception) {
                _stepDetailLiveData.postValue(Resource.error(null, e.localizedMessage))
            }
        }
    }
}