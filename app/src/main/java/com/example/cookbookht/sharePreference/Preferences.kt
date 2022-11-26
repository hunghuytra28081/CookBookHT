package com.example.cookbookht.sharePreference

import android.content.SharedPreferences
import com.f2prateek.rx.preferences2.RxSharedPreferences

class Preferences(
    private val rxPrefs: RxSharedPreferences,
    private val sharedPrefs: SharedPreferences
) {

    val isFistTime = rxPrefs.getBoolean("isFistTime", true)
    val isLanguageVi = rxPrefs.getString("isLanguageVi", "vi")
}