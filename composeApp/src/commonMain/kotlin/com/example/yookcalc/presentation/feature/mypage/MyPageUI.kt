package com.example.yookcalc.presentation.feature.mypage

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier


@Composable
fun MyPageUI(onEvent: (MyPageEvent) -> Unit = {}) {
    println("[UI] MyPageUI 실행됨")
    Column(modifier = Modifier.fillMaxSize()) {
        Text("마이페이지 화면")

        Button(onClick = {
            println("[UI] 설정 화면으로 이동 버튼 클릭됨")
            onEvent(MyPageEvent.NavigateToSetting) // 이벤트 발생
        }) {
            Text("설정 화면으로 이동")
        }
    }
}

