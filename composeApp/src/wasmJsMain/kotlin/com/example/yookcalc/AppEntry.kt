package com.example.yookcalc

import androidx.compose.runtime.Composable

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import com.example.yookcalc.presentation.ui.maternity.MaternityLeaveScreen
import com.example.yookcalc.presentation.MaternityLeaveViewModel
import com.example.yookcalc.domain.MaternityLeaveCalculator

import com.example.yookcalc.presentation.theme.Theme

@Composable
fun AppEntry() {
    Theme {
        Surface {
            // 임시로 ViewModel을 직접 생성하여 화면을 표시합니다.
            // (실제로는 Koin 등을 통해 주입받아야 함)
            val viewModel = MaternityLeaveViewModel()
            MaternityLeaveScreen(viewModel)
        }
    }
}


