package com.example.yookcalc.data.storage

import android.content.Context

actual object StorageProvider {
    private lateinit var context: Context

    fun init(context: Context) {
        this.context = context
    }

    actual fun providePreferenceStorage(): PreferenceStorage {
        return AndroidPreferenceStorage(context)
    }
}