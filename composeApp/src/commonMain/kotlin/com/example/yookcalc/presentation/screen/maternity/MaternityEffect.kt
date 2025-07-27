package com.example.yookcalc.presentation.screen.maternity

import com.example.yookcalc.presentation.base.UiEffect

// 일회성 액셩
// 예 : “SnackBar 띄우기” → ShowSnackBar(val message: String)
sealed interface MaternityEffect : UiEffect {
    data class ShowToast(val message: String) : MaternityEffect
}