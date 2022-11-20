package com.example.cookbookht.data.repository.source.remote.api.translate

import android.util.Log
import com.example.cookbookht.utils.Constant.BASE_URL_TRANSLATE
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object RetrofitBuilder {

    private fun getRetrofit(): Retrofit {

        val logging = HttpLoggingInterceptor {
            Log.d("Main12345","--------------------- LOGGER ---------------------------")
            Log.d("Main12345", it)
        }
        logging.level = HttpLoggingInterceptor.Level.BODY


        val client = OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()

        return Retrofit.Builder()
            .baseUrl(BASE_URL_TRANSLATE)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

    val apiService: APITranslate = getRetrofit().create(APITranslate::class.java)
}