package com.example.yookcalc.data.storage

actual object StorageProvider {
    actual fun providePreferenceStorage(): PreferenceStorage = WebPreferenceStorage()
}