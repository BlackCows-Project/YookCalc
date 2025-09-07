package com.example.yookcalc.navigation

import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import cafe.adriel.voyager.navigator.tab.Tab

// 하단 탭 UI
@Composable
fun BottomNavigationBar(
    currentTab: Tab,
    onTabSelected: (Tab) -> Unit
) {
    NavigationBar(containerColor = Color.White) {
        appTabsList.forEach { tab ->
            NavigationBarItem(
                selected = currentTab == tab,
                onClick = { onTabSelected(tab) },
                icon = {
//                    Icon(
//                    imageVector = when(tab) {
//                        is AppTab.Maternity -> tab.tabIcon
//                        is AppTab.Parental -> tab.tabIcon
//                        is AppTab.ShortenedWork -> tab.tabIcon
//                        is AppTab.MyPage -> tab.tabIcon
//                    },
//                    contentDescription = tab.options.title
//                )
                       }, // 아이콘 나중에 추가 가능
                label = { Text(text = tab.options.title) } // 여기서 String 값 추출됨
            )
        }
    }
}