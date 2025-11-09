package com.example.yookcalc.navigation

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.example.yookcalc.presentation.feature.maternity.MaternityUI
import com.example.yookcalc.presentation.feature.parental.ParentalUI
import com.example.yookcalc.presentation.feature.shortened_work.ShortenedWorkUI
import com.example.yookcalc.ui.MyPageScreen


sealed class AppTab(
    private val tabIndex: UShort,
    private val tabTitle: String
) : Tab {

    override val options: TabOptions
        @Composable get() = TabOptions(index = tabIndex, title = tabTitle)

    object Maternity : AppTab(0u, "출산휴직") {
        @Composable
        override fun Content() {
            MaternityUI()
        }
    }

    object Parental : AppTab(1u, "육아휴직") {
        @Composable
        override fun Content() {
            ParentalUI()
        }
    }

    object ShortenedWork : AppTab(2u, "근로시간 단축") {
        @Composable
        override fun Content() {
            ShortenedWorkUI()
        }
    }

    object MyPage : AppTab(3u, "마이페이지") {
        @Composable
        override fun Content() {
            Navigator(MyPageScreen)
        }
    }
}