package com.example.yookcalc.presentation.feature.mypage

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.yookcalc.navigation.NavigationController
import com.example.yookcalc.navigation.NavigationRoute
import kotlinx.coroutines.flow.collectLatest

@Composable
fun MyPageUi(viewModel: MyPageViewModel = viewModel()) {
    val state = viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.effect.collectLatest { effect ->
            when (effect) {
                is MyPageEffect.ShowToast -> println("Toast: ${effect.message}")
                MyPageEffect.NavigateToSetting -> {
                    println("[MyPageUi] Navigate to SettingScreen triggered")
                    NavigationController.navigateTo(NavigationRoute.Setting) // ← 네비게이션 호출
                }
            }
        }
    }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("마이페이지")
        Button(
            onClick = { viewModel.onEvent(MyPageEvent.OnProfileClicked) },
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text("프로필 클릭")
        }
        Button(
            onClick = { viewModel.onEvent(MyPageEvent.OnSettingClicked) },
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text("설정으로 이동")
        }
        Text(state.value.message, modifier = Modifier.padding(top = 16.dp))
    }
}