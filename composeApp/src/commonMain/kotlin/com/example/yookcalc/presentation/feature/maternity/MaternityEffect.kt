package com.example.yookcalc.presentation.feature.maternity

import com.example.yookcalc.base.UiEffect

// 일회성 액셩
// 예 : “SnackBar 띄우기” → ShowSnackBar(val message: String)
sealed interface MaternityEffect : UiEffect {

    data class ShowToast(val message: String) : MaternityEffect
    data class ShowError(val message: String) : MaternityEffect
    data class ShowCalculationResult(val result: String) : MaternityEffect}