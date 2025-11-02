package com.example.yookcalc.data.storage

expect object StorageProvider {
    fun providePreferenceStorage(): PreferenceStorage
}