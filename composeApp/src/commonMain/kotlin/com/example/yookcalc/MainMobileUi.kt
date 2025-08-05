package com.example.yookcalc

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import com.example.yookcalc.navigation.NavigationController
import com.example.yookcalc.navigation.NavigationRoute
import com.example.yookcalc.presentation.feature.maternity.MaternityUi
import com.example.yookcalc.presentation.feature.mypage.MyPageUi
import com.example.yookcalc.presentation.feature.parental.ParentalUi
import com.example.yookcalc.presentation.feature.setting.SettingUi
import com.example.yookcalc.presentation.feature.shortened_work.ShortenedWorkUi


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainMobileUi() {
    val currentRoute = NavigationController.currentState().collectAsState()

    Scaffold(
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    selected = currentRoute.value == NavigationRoute.Maternity,
                    onClick = { NavigationController.navigateTo(NavigationRoute.Maternity) },
                    icon = {}, // 아이콘 제거
                    label = { Text("출산휴가") }
                )
                NavigationBarItem(
                    selected = currentRoute.value == NavigationRoute.Parental,
                    onClick = { NavigationController.navigateTo(NavigationRoute.Parental) },
                    icon = {},
                    label = { Text("육아휴직") }
                )
                NavigationBarItem(
                    selected = currentRoute.value == NavigationRoute.ShortenedWork,
                    onClick = { NavigationController.navigateTo(NavigationRoute.ShortenedWork) },
                    icon = {},
                    label = { Text("단축근로") }
                )
                NavigationBarItem(
                    selected = currentRoute.value == NavigationRoute.MyPage,
                    onClick = { NavigationController.navigateTo(NavigationRoute.MyPage) },
                    icon = {},
                    label = { Text("마이페이지") }
                )
            }
        }
    ) { innerPadding ->
        Box(Modifier.padding(innerPadding)) {
            when (currentRoute.value) {
                is NavigationRoute.Maternity -> MaternityUi()
                is NavigationRoute.Parental -> ParentalUi()
                is NavigationRoute.ShortenedWork -> ShortenedWorkUi()
                is NavigationRoute.MyPage -> MyPageUi()
                is NavigationRoute.Setting -> SettingUi() // ← 추가
                else -> MaternityUi() // 기본 화면
            }
        }
    }
}