package com.example.yookcalc.data.storage

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

@Composable
actual fun initializeStorage() {
    val context = LocalContext.current
    StorageProvider.init(context)
}