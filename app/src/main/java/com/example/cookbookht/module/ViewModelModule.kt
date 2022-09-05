package com.example.cookbookht.module

import com.example.cookbookht.ui.detail.RecipeDetailViewModel
import com.example.cookbookht.ui.detailContent.detailIngredient.IngredientDetailViewModel
import com.example.cookbookht.ui.detailContent.detailNutrient.NutrientDetailViewModel
import com.example.cookbookht.ui.home.HomeViewModel
import com.example.cookbookht.ui.search.SearchViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { HomeViewModel(get()) }
    viewModel { RecipeDetailViewModel(get()) }
    viewModel { IngredientDetailViewModel(get()) }
    viewModel { NutrientDetailViewModel(get()) }
    viewModel { SearchViewModel(get()) }
}
