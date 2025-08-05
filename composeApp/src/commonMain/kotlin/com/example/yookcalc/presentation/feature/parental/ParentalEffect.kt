package com.example.yookcalc.presentation.feature.parental

import com.example.yookcalc.base.UiEffect

sealed interface ParentalEffect : UiEffect {
    data class ShowToast(val message: String) : ParentalEffect
}