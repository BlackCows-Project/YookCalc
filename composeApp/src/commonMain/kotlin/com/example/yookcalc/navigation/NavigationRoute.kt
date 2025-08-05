package com.example.yookcalc.navigation

// 화면(출산휴가, 육아휴직, 근로시간 단축, 마이페이지) 경로 Enum
sealed class NavigationRoute(val route: String) {

    // 앱
    object Maternity : NavigationRoute("maternity")
    object Parental : NavigationRoute("parental")
    object ShortenedWork : NavigationRoute("shortened_work")
    object MyPage : NavigationRoute("mypage")
    object Setting : NavigationRoute("setting")


    // 웹
    object Home : NavigationRoute("home")
    object Next : NavigationRoute("next")
}