package com.example.cookbookht.data.repository.source.remote.api.translate

import com.example.cookbookht.data.model.translate.TranslateResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface APITranslate {

    //https://translation.googleapis.com/language/translate/v2?q=table&source=en&target=vi&format=text&key=AIzaSyDUfVcopSoOjEwpy9Am1jWiqkbi1AEre8I
    @GET("/language/translate/v2")
    fun getDataTranslate(
        @Query("q") q: String,
        @Query("key") key: String,
        @Query("target") target: String = "vi",
        @Query("format") format: String = "text",
        @Query("source") source: String = "en"
    ): TranslateResponse
}