package com.example.yookcalc.data.storage

import kotlinx.browser.localStorage

class WebPreferenceStorage : PreferenceStorage {
    override fun getString(key: String): String? = localStorage.getItem(key)

    override fun putString(
        key: String,
        value: String,
    ) {
        localStorage.setItem(key, value)
    }

    override fun getLong(
        key: String,
        defaultValue: Long,
    ): Long {
        val value = localStorage.getItem(key)
        return value?.toLongOrNull() ?: defaultValue
    }

    override fun putLong(
        key: String,
        value: Long,
    ) {
        localStorage.setItem(key, value.toString())
    }

    override fun remove(key: String) {
        localStorage.removeItem(key)
    }

    override fun clear() {
        localStorage.clear()
    }
}