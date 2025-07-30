package com.example.yookcalc.screen

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import com.example.yookcalc.presentation.feature.setting.SettingUI

object SettingScreen : Screen {
    @Composable
    override fun Content() {
        println("[Screen] SettingScreen Content 실행됨")
        SettingUI() // UI 호출
    }
}
