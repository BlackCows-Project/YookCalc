package com.example.yookcalc.presentation.feature.setting

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun SettingUI() {
    println("[UI] SettingUI 실행됨")
    Column(modifier = Modifier.fillMaxSize()) {
        Text("설정 화면")
    }
}