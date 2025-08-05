package com.example.yookcalc.presentation.feature.shortened_work


import com.example.yookcalc.base.UiEffect

sealed interface ShortenedWorkEffect : UiEffect {
    data class ShowToast(val message: String) : ShortenedWorkEffect
}