package com.example.yookcalc

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.example.yookcalc.navigation.NavigationController
import com.example.yookcalc.navigation.NavigationRoute

@Composable
fun WebApp() {
    println("=== WebApp 실행됨 ===")
    val currentRoute by NavigationController.currentState().collectAsState()

    MaterialTheme {
        Surface {
            when (currentRoute) {
                is NavigationRoute.Home -> HomeUi()
                is NavigationRoute.Next -> NextUi()
                else -> HomeUi()
            }
        }
    }

}