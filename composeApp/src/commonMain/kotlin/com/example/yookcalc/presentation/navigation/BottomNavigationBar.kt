package com.example.yookcalc.presentation.navigation

import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import cafe.adriel.voyager.navigator.tab.Tab

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
                icon = {}, // 아이콘 나중에 추가 가능
                label = { Text(text = tab.options.title) } // 여기서 String 값 추출됨
            )
        }
    }
}