package com.example.yookcalc

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.navigator.tab.CurrentTab
import cafe.adriel.voyager.navigator.tab.TabNavigator
import com.example.yookcalc.presentation.navigation.AppTab
import com.example.yookcalc.presentation.navigation.BottomNavigationBar
import com.example.yookcalc.presentation.theme.Theme

@Composable
fun App() {
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