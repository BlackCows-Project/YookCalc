package com.example.yookcalc.navigation

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

// 현재 선택된 화면 관리
class NavigationState(initial: NavigationRoute = NavigationRoute.Maternity) {
    private val _currentRoute = MutableStateFlow(initial)
    val currentRoute: StateFlow<NavigationRoute> get() = _currentRoute

    fun navigateTo(route: NavigationRoute) {
        _currentRoute.value = route
    }
}