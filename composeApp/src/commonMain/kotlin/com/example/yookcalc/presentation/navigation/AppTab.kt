package com.example.yookcalc.presentation.navigation

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.example.yookcalc.presentation.screen.maternity.MaternityScreenUI
import com.example.yookcalc.presentation.screen.mypage.MyPageScreenUI
import com.example.yookcalc.presentation.screen.parental.ParentalScreenUI
import com.example.yookcalc.presentation.screen.shortened_work.ShortenedWorkScreenUI


sealed class AppTab(
    private val tabIndex: UShort,
    private val tabTitle: String,
    private val contentLambda: @Composable () -> Unit
) : Tab {
    override val options: TabOptions
        @Composable get() = TabOptions(index = tabIndex, title = tabTitle)

    @Composable
    override fun Content() = contentLambda()

    object Maternity : AppTab(0u, "출산휴직", { MaternityScreenUI() })
    object Parental : AppTab(1u, "육아휴직", { ParentalScreenUI() })
    object ShortenedWork : AppTab(2u, "근로시간 단축", { ShortenedWorkScreenUI() })
    object MyPage : AppTab(3u, "마이페이지", { MyPageScreenUI() })
}