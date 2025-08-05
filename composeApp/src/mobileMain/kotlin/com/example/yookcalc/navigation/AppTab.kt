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
    private val tabTitle: String,
    private val contentLambda: @Composable () -> Unit
) : Tab {
    override val options: TabOptions
        @Composable get() = TabOptions(index = tabIndex, title = tabTitle)

    @Composable
    override fun Content() = contentLambda()

    object Maternity : AppTab(0u, "출산휴직", {
        MaternityUI() })
    object Parental : AppTab(1u, "육아휴직", {
        ParentalUI() })
    object ShortenedWork : AppTab(2u, "근로시간 단축", {
        ShortenedWorkUI() })
    object MyPage : AppTab(3u, "마이페이지", {
        Navigator(MyPageScreen)// ← **중요: 여기서 Navigator 시작**
    })
}