package com.example.yookcalc.presentation.feature.shortened_work


import com.example.yookcalc.base.UiState

data class ShortenedWorkState(
    val workHours: String = "",
    val result: String = ""
) : UiState