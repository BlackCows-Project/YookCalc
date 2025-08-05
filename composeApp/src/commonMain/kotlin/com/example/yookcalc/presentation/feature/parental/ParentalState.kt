package com.example.yookcalc.presentation.feature.parental

import com.example.yookcalc.base.UiState

data class ParentalState(
    val startDate: String = "",
    val endDate: String = "",
    val result: String = ""
) : UiState
