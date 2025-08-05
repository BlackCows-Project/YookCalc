package com.example.yookcalc.presentation.feature.maternity

import com.example.yookcalc.base.UiState

data class MaternityState(
    val startDate: String = "",
    val endDate: String = "",
    val result: String = ""
) : UiState