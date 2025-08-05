package com.example.yookcalc

import androidx.compose.runtime.Composable

@Composable
actual fun MainPlatformUi() {
    MainMobileUi()  // 기존 Android+iOS UI
}