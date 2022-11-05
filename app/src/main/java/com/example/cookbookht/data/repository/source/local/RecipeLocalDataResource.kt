package com.example.cookbookht.data.repository.source.local

import com.example.cookbookht.data.repository.source.RecipeDataSource
import com.example.cookbookht.data.repository.source.local.entities.Favorite
import com.example.cookbookht.data.repository.source.local.entities.History

class RecipeLocalDataResource( private val recipeDao: RecipeDao) : RecipeDataSource.Local {

    override suspend fun getAllHistory():List<History> {
        return recipeDao.getAllHistory()
    }

    override suspend fun insertHistory(item: History) {
        return recipeDao.insert(item)
    }

    override suspend fun deleteHistory(name: String) {
        recipeDao.deleteName(name)
    }

    override fun isExistHistory(name: String) {
        recipeDao.isExists(name)
    }

    //Favorite
    override suspend fun getAllFavorite(): List<Favorite> {
        return recipeDao.getAllFavorite()
    }

    override suspend fun insertFavorite(item: Favorite) {
        recipeDao.insertFavorite(item)
    }

    override suspend fun deleteFavorite(favorite: Favorite) {
        recipeDao.deleteFavorite(favorite)
    }

    override suspend fun alreadyFavorite(id: Int?): Favorite {
        return recipeDao.isFavorite(id)
    }
}