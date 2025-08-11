package com.example.yookcalc.navigation

// import androidx.compose.material3.Icon // Icon 임포트
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

/**
 * 웹 애플리케이션의 하단 탭 바 UI Composable입니다.
 * 선택된 탭을 표시하고 탭 선택 이벤트를 처리합니다.
 */
@Composable
fun WebBottomNavigationBar(
    currentTab: WebTab, // 현재 선택된 탭
    onTabSelected: (WebTab) -> Unit // 탭 선택 시 호출될 람다 함수
) {
    NavigationBar(containerColor = Color.White) { // 배경색을 흰색으로 설정
        WebTab.allTabs.forEach { tab -> // 정의된 모든 웹 탭을 순회합니다.
            NavigationBarItem(
                selected = currentTab == tab, // 현재 탭과 일치하면 선택된 상태로 표시
                onClick = { onTabSelected(tab) }, // 클릭 시 onTabSelected 람다 호출
                icon = { /* 아이콘은 현재 표시하지 않습니다. */ }, // 아이콘 Composable
                label = { Text(text = tab.title) } // 탭 제목 텍스트
            )
        }
    }
}