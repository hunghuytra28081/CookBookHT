package com.example.cookbookht.module

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.example.cookbookht.sharePreference.Preferences
import com.example.cookbookht.utils.Constant
import com.f2prateek.rx.preferences2.RxSharedPreferences
import org.koin.dsl.module

val appModule = module {

    fun provideSharedPreferences(context: Context): SharedPreferences =
        PreferenceManager.getDefaultSharedPreferences(context)

    fun provideRxSharedPreferences(sharedPreferences: SharedPreferences): RxSharedPreferences =
        RxSharedPreferences.create(sharedPreferences)

    fun providePreferences(
        rxSharedPreferences: RxSharedPreferences,
        sharedPreferences: SharedPreferences
    ): Preferences =
        Preferences(rxSharedPreferences, sharedPreferences)

    single { provideSharedPreferences(get()) }
    single { provideRxSharedPreferences(get()) }
    single { providePreferences(get(),get()) }
}