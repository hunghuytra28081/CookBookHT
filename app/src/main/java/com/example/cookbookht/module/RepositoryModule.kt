package com.example.cookbookht.module

import com.example.cookbookht.data.repository.RecipeRepository
import org.koin.dsl.module

val repositoryModule = module {
    single { RecipeRepository(get(), get()) }
}
