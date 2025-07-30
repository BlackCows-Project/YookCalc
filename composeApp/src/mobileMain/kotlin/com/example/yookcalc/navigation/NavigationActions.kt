package com.example.yookcalc.navigation

import cafe.adriel.voyager.navigator.Navigator
import com.example.yookcalc.screen.SettingScreen

// 스택 기반 네비게이션 (navigator.push, pop)
// 필요한 이동 함수들을 계속 추가 가능
object NavigationActions {

    fun navigateToSetting(navigator: Navigator) {
        println("[Navigation] SettingScreen으로 이동 요청")
        navigator.push(SettingScreen) // SettingScreen을 스택에 추가
    }
}
