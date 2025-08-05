package com.example.yookcalc

import androidx.compose.runtime.Composable
import com.example.yookcalc.theme.Theme

@Composable
fun MobileApp() {
    Theme {
        MainPlatformUi()   // 플랫폼별 UI (현재는 Android+iOS -> MainMobileUi)
    }
}