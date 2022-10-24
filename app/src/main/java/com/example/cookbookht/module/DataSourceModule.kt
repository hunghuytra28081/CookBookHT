package com.example.cookbookht.module

import com.example.cookbookht.data.repository.source.RecipeDataSource
import com.example.cookbookht.data.repository.source.local.RecipeLocalDataResource
import com.example.cookbookht.data.repository.source.remote.RecipeRemoteDataSource
import org.koin.dsl.bind
import org.koin.dsl.module

val dataSourceModule = module {
    single { RecipeRemoteDataSource(get()) } bind (RecipeDataSource.Remote::class)
    single { RecipeLocalDataResource(get()) } bind (RecipeDataSource.Local::class)
}
