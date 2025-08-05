package com.example.yookcalc.navigation

import androidx.compose.runtime.*
import androidx.compose.material3.*
import androidx.compose.foundation.layout.*
// import androidx.compose.ui.graphics.vector.ImageVector // 아이콘을 위한 ImageVector 임포트
// import androidx.compose.material.icons.Icons // Material Icons 임포트
// import androidx.compose.material.icons.filled.Home // 예시 아이콘 임포트
// import androidx.compose.material.icons.filled.List
// import androidx.compose.material.icons.filled.Settings
// import androidx.compose.material.icons.filled.Person


/**
 * 웹 애플리케이션의 하단 탭을 정의하는 Sealed Class입니다.
 * 각 탭은 고유한 제목을 가집니다. (아이콘 제거됨)
 */
sealed class WebTab(val title: String) { // ImageVector icon 파라미터
    object Maternity : WebTab("출산휴직") // Icons.Default.Home
    object Parental : WebTab("육아휴직") // Icons.Default.List
    object ShortenedWork : WebTab("근로시간 단축") // Icons.Default.Settings
    object MyPage : WebTab("마이페이지") // Icons.Default.Person

    // 모든 탭 목록을 반환하는 프로퍼티
    companion object {
        val allTabs = listOf(Maternity, Parental, ShortenedWork, MyPage)
    }
}