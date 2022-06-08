package com.example.cookbookht.data.repository.source.remote.api

import retrofit2.Retrofit

class RetrofitClient(private val baseRetrofit: Retrofit) {

    fun <T> create(service: Class<T>): T {
        return baseRetrofit.create(service)
    }
}
