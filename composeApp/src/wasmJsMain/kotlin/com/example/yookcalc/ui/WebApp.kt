package com.example.yookcalc.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.yookcalc.navigation.WebBottomNavigationBar
import com.example.yookcalc.navigation.WebTab
import com.example.yookcalc.ui.screen.MaternityScreen
import com.example.yookcalc.ui.screen.MyPageScreen
import com.example.yookcalc.ui.screen.ParentalScreen
import com.example.yookcalc.ui.screen.ShortenedWorkScreen
import com.example.yookcalc.theme.AppTheme // AppTheme 임포트

/**
 * 웹 애플리케이션의 메인 Composable 함수입니다.
 * 하단 탭 바와 현재 선택된 탭의 콘텐츠를 관리합니다.
 */
@Composable
fun WebApp() {
    // 현재 선택된 탭 상태를 관리합니다. 초기 탭은 Maternity로 설정합니다.
    var currentTab by remember { mutableStateOf<WebTab>(WebTab.Maternity) }

    AppTheme { // 앱의 전역 테마를 적용합니다.
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Scaffold(
                bottomBar = {
                    // 하단 탭 바 Composable을 호출합니다.
                    WebBottomNavigationBar(
                        currentTab = currentTab,
                        onTabSelected = { tab -> currentTab = tab } // 탭 선택 시 상태 업데이트
                    )
                }
            ) { paddingValues ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues) // Scaffold의 패딩을 적용합니다.
                ) {
                    // 현재 선택된 탭에 따라 다른 화면 Composable을 표시합니다.
                    when (currentTab) {
                        WebTab.Maternity -> MaternityScreen()
                        WebTab.Parental -> ParentalScreen()
                        WebTab.ShortenedWork -> ShortenedWorkScreen()
                        WebTab.MyPage -> MyPageScreen()
                    }
                }
            }
        }
    }
}