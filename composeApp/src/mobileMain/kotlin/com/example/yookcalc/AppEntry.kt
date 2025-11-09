package com.example.yookcalc

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.navigator.tab.CurrentTab
import cafe.adriel.voyager.navigator.tab.TabNavigator
import com.example.yookcalc.data.storage.initializeStorage
import com.example.yookcalc.domain.usecase.MaternityLeaveSettingUseCase
import com.example.yookcalc.domain.usecase.ParentalLeaveSettingUseCase
import com.example.yookcalc.navigation.AppTab
import com.example.yookcalc.navigation.BottomNavigationBar
import com.example.yookcalc.presentation.theme.Theme
import kotlinx.coroutines.flow.combine
import com.example.yookcalc.di.appModules
import kotlinx.coroutines.flow.takeWhile
import org.koin.compose.KoinApplication

// TabNavigator로 탭 네비게이션 시작
// Android와 iOS는 모두 이 AppEntry를 사용
@Composable
fun AppEntry() {
    initializeStorage()

    var isLoading by remember { mutableStateOf(true) }
    val parentalLeaveUseCase = remember { ParentalLeaveSettingUseCase() }
    val maternityLeaveUseCase = remember { MaternityLeaveSettingUseCase() }

    LaunchedEffect(Unit) {
        combine(
            parentalLeaveUseCase(),
            maternityLeaveUseCase(),
        ) { _, _ -> Unit }
            .takeWhile { isLoading }
            .collect { isLoading = false }
    }

    Theme {
        if (isLoading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            KoinApplication(application = {
                // Koin DI 초기화 추가
                modules(appModules)
            }) {
                Theme {
                    TabNavigator(AppTab.Maternity) { tabNavigator ->
                        Scaffold(
                            bottomBar = {
                                BottomNavigationBar(
                                    currentTab = tabNavigator.current,
                                    onTabSelected = { tabNavigator.current = it }
                                )
                            }
                        ) { paddingValues ->
                            Surface(modifier = Modifier.padding(paddingValues)) {
                                CurrentTab()
                            }
                        }
                    }
                }
            }
        }
    }
}