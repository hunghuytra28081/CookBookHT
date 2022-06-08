package com.example.cookbookht.module

import com.example.cookbookht.data.repository.source.remote.api.APIService
import com.example.cookbookht.data.repository.source.remote.api.RetrofitClient
import com.example.cookbookht.utils.Constant
import com.example.cookbookht.utils.Constant.BASE_URL
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val apiModule = module {

    fun provideAPIService(retrofit: RetrofitClient) = retrofit.create(APIService::class.java)

    single { provideAPIService(get()) }
}

val retrofitModule = module {

    fun provideGson() = GsonBuilder().create()

    fun provideGsonConverterFactory(factory: Gson) = GsonConverterFactory.create(factory)

    fun provideHttpClient(): OkHttpClient {
        return OkHttpClient.Builder().addInterceptor { chain ->
            val request = chain.request().newBuilder()
            val originalHttpUrl = chain.request().url().newBuilder()
            val url = originalHttpUrl.addQueryParameter(
                Constant.API_KEY_WORD,
                Constant.API_KEY
            ).build()
            request.url(url)
            return@addInterceptor chain.proceed(request.build())
        }
            .build()
    }

    fun provideRetrofit(factory: GsonConverterFactory, client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(factory)
            .client(client)
            .build()
    }

    fun provideClient(retrofit: Retrofit): RetrofitClient {
        return RetrofitClient(retrofit)
    }

    single { provideGson() }
    single { provideHttpClient() }
    single { provideGsonConverterFactory(get()) }
    single { provideRetrofit(get(), get()) }
    single { provideClient(get()) }
}
