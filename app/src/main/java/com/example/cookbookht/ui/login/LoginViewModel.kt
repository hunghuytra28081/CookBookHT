package com.example.cookbookht.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cookbookht.data.repository.RecipeRepository
import com.example.cookbookht.data.repository.source.local.entities.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginViewModel(
    private val repository: RecipeRepository
) : ViewModel() {

    fun insertUser(user: User) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                repository.insertUser(user)
            } catch (ex: Exception) {
                ex.printStackTrace()
            }
        }
    }

    fun checkUser(userName: String, pass: String): User? {
        return repository.login(userName, pass)
    }
}