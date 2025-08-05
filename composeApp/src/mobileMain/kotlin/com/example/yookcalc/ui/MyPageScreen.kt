package com.example.yookcalc.ui

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.example.yookcalc.navigation.NavigationActions
import com.example.yookcalc.presentation.feature.mypage.MyPageEvent
import com.example.yookcalc.presentation.feature.mypage.MyPageUI

object MyPageScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow

        println("[Screen] MyPageScreen 실행됨")

        MyPageUI { event ->
            when (event) {
                is MyPageEvent.NavigateToSetting -> {
                    println("[Event] NavigateToSetting 이벤트 발생 → SettingScreen으로 이동")
                    NavigationActions.navigateToSetting(navigator)
                }
                is MyPageEvent.LoadDefaultData -> {
                    println("[Event] LoadDefaultData 이벤트 발생")
                }
            }
        }
    }
}