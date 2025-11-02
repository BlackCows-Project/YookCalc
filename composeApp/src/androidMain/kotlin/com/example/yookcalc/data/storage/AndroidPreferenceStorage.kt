package com.example.yookcalc.data.storage

import android.content.Context
import android.content.SharedPreferences

class AndroidPreferenceStorage(context: Context) : PreferenceStorage {
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("yook_calc_prefs", Context.MODE_PRIVATE)

    override fun getString(key: String): String? = sharedPreferences.getString(key, null)

    override fun putString(
        key: String,
        value: String,
    ) {
        sharedPreferences.edit().putString(key, value).apply()
    }

    override fun getLong(
        key: String,
        defaultValue: Long,
    ): Long = sharedPreferences.getLong(key, defaultValue)

    override fun putLong(
        key: String,
        value: Long,
    ) {
        sharedPreferences.edit().putLong(key, value).apply()
    }

    override fun remove(key: String) {
        sharedPreferences.edit().remove(key).apply()
    }

    override fun clear() {
        sharedPreferences.edit().clear().apply()
    }
}