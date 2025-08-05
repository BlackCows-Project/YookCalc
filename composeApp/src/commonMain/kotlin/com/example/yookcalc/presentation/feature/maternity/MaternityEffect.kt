package com.example.yookcalc.presentation.feature.maternity

import com.example.yookcalc.base.UiEffect

sealed interface MaternityEffect : UiEffect {
    data class ShowToast(val message: String) : MaternityEffect
}