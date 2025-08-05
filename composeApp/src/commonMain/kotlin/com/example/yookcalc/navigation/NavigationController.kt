package com.example.yookcalc.navigation

// 화면 전환 함수(navigateTo) 제공
object NavigationController {
    private val navState = NavigationState()

    fun currentState() = navState.currentRoute
    fun navigateTo(route: NavigationRoute) = navState.navigateTo(route)
}