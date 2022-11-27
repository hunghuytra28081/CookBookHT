package com.example.cookbookht.extension

import android.os.SystemClock
import android.view.View
import com.example.cookbookht.data.repository.source.remote.api.translate.RetrofitBuilder
import com.example.cookbookht.sharePreference.Preferences
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

fun CoroutineScope.translateToVi(prefs : Preferences, data: String?): String {
    var value = ""
    this.executeAsyncTask(onPreExecute = {}, doInBackground = {
        return@executeAsyncTask when (prefs.isLanguageVi.get()) {
//            "vi" -> {
//                data?.let {
//                    val response = RetrofitBuilder.apiService.getDataTranslate(
//                        it, API_KEY_TRANSLATE
//                    )
//                    response.let { response ->
//                        response.data?.translations?.joinToString { it.translatedText }
//                    }
//                }
//            }
            else -> data
        }
    }, onPostExecute = {
        value = it.toString()
    })
    return value
}

fun CoroutineScope.translateToVie(prefs : Preferences, data: String?): String? {
    var value: String? = ""
    this.launch(Dispatchers.IO) {
        try {
            this.executeAsyncTask(onPreExecute = {}, doInBackground = {
                return@executeAsyncTask when (prefs.isLanguageVi.get()) {
//                    "vi" -> {
//                        data?.let {
//                            val response = RetrofitBuilder.apiService.getDataTranslate(
//                                it, API_KEY_TRANSLATE
//                            )
//                            response.let { response ->
//                                response.data?.translations?.joinToString { it.translatedText }
//                            }
//                        }
//                    }
                    else -> data
                }
            }, onPostExecute = {
                value = it.toString()
            })

        }catch (e: Exception) {

        }
    }
    return value
}

fun View.clickWithDebounce(debounceTime: Long = 1200L, action: () -> Unit) {
    this.setOnClickListener(object : View.OnClickListener {
        private var lastClickTime: Long = 0
        override fun onClick(v: View) {
            if (SystemClock.elapsedRealtime() - lastClickTime < debounceTime) return
            else action()
            lastClickTime = SystemClock.elapsedRealtime()
        }
    })
}



