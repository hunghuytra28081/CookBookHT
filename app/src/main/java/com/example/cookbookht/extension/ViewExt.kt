package com.example.cookbookht.extension

import android.view.View
import com.example.cookbookht.data.repository.source.remote.api.translate.RetrofitBuilder
import com.example.cookbookht.utils.Constant.API_KEY_TRANSLATE
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.await
import java.util.*


fun View.toVisible() {
    this.visibility = View.VISIBLE
}

fun View.toGone() {
    this.visibility = View.GONE
}

fun View.viewVisible() {
    this.visibility = View.VISIBLE
}

fun View.viewInVisible() {
    this.visibility = View.INVISIBLE
}

fun <R> CoroutineScope.executeAsyncTask(
    onPreExecute: () -> Unit,
    doInBackground: () -> R,
    onPostExecute: (R) -> Unit,
) = launch {
    onPreExecute()
    val result =
        withContext(Dispatchers.IO) { // runs in background thread without blocking the Main Thread
            doInBackground()
        }
    onPostExecute(result)
}

fun CoroutineScope.translateToVi(data: String?): String {
    var value = ""
    this.executeAsyncTask(onPreExecute = {}, doInBackground = {
        return@executeAsyncTask when (Locale.getDefault().language) {
            "vi" -> {
                data?.let {
                    val response = RetrofitBuilder.apiService.getDataTranslate(
                        it, API_KEY_TRANSLATE
                    )
                    response.let { response ->
                        response.data?.translations?.joinToString { it.translatedText }
                    }
                }
            }
            else -> data
        }
    }, onPostExecute = {
        value = it.toString()
    })
    return value
}

fun String.translateToVi(): String? {
    var value : String? = ""
    when (Locale.getDefault().language) {
            "vi" -> {
                this?.let {
                    val response = RetrofitBuilder.apiService.getDataTranslate(
                        it, API_KEY_TRANSLATE
                    )
                   value =  response.let { response ->
                        response.data?.translations?.joinToString { it.translatedText }
                    }
                }
            }
            else -> value = this
        }

    return value
}

