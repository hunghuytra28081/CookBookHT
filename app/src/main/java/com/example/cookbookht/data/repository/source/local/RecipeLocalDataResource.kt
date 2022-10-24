package com.example.cookbookht.data.repository.source.local

import com.example.cookbookht.data.repository.source.RecipeDataSource

class RecipeLocalDataResource( private val historyDao: HistoryDao) : RecipeDataSource.Local {

    override suspend fun getAllHistory():List<History> {
        return historyDao.getAllHistory()
    }

    override suspend fun insertHistory(item: History) {
        return historyDao.insert(item)
    }

    override suspend fun deleteHistory(name: String) {
        historyDao.deleteName(name)
    }

    override fun isExistHistory(name: String) {
        historyDao.isExists(name)
    }
}