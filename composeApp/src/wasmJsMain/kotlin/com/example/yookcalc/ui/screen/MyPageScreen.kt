package com.example.yookcalc.ui.screen

import androidx.compose.runtime.Composable
import androidx.compose.material3.Text
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * 웹용 마이페이지 화면 Composable입니다.
 * 설정 화면으로 이동하는 버튼을 포함합니다.
 */
@Composable
fun MyPageScreen() {
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "웹 마이페이지 화면입니다!",
            style = MaterialTheme.typography.headlineMedium
        )
        Text(
            text = "여기에 마이페이지 UI가 들어갑니다.",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(top = 8.dp)
        )
        // 웹에서는 Voyager Navigator를 사용하지 않으므로, 설정 화면 이동은 추후 웹 전용 네비게이션 로직으로 구현해야 합니다.
        // 현재는 버튼만 표시합니다.
        Button(
            onClick = { println("웹에서 설정 화면으로 이동 요청 (미구현)") },
            modifier = Modifier.padding(top = 24.dp)
        ) {
            Text("설정 화면 (미구현)")
        }
    }
}