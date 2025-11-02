package com.example.yookcalc.data.storage

interface PreferenceStorage {
    fun getString(key: String): String?
    fun putString(key: String, value: String)
    fun getLong(key: String, defaultValue: Long = 0L): Long
    fun putLong(key: String, value: Long)
    fun remove(key: String)
    fun clear()
}