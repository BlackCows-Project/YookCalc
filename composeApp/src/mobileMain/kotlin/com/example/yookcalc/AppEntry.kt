package com.example.yookcalc

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.navigator.tab.CurrentTab
import cafe.adriel.voyager.navigator.tab.TabNavigator
import com.example.yookcalc.navigation.AppTab
import com.example.yookcalc.navigation.BottomNavigationBar
import com.example.yookcalc.presentation.theme.Theme

// TabNavigator로 탭 네비게이션 시작
// Android와 iOS는 모두 이 AppEntry를 사용
@Composable
fun AppEntry() {
    Theme {
        TabNavigator(AppTab.Maternity) { tabNavigator ->
            Scaffold(
                bottomBar = {
                    BottomNavigationBar(
                        currentTab = tabNavigator.current,
                        onTabSelected = { tabNavigator.current = it }
                    )
                }
            ) { paddingValues ->
                Surface(modifier = Modifier.padding(paddingValues)) {
                    CurrentTab()
                }
            }
        }
    }
}