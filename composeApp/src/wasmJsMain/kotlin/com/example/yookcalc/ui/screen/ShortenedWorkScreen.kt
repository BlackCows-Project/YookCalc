package com.example.yookcalc.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * 웹용 근로시간 단축 계산기 화면 Composable입니다.
 * 실제 계산기 UI가 여기에 구현됩니다.
 */
@Composable
fun ShortenedWorkScreen() {
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "웹 근로시간 단축 계산기 화면입니다!",
            style = MaterialTheme.typography.headlineMedium
        )
        Text(
            text = "여기에 근로시간 단축 계산기 UI가 들어갑니다.",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(top = 8.dp)
        )
    }
}