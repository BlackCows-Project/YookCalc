package com.example.yookcalc.presentation.feature.mypage

import com.example.yookcalc.base.UiState

data class MyPageState(
    val userName: String = "사용자",
    val message: String = ""
) : UiState