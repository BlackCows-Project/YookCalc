package com.example.yookcalc.presentation.feature.parental

import com.example.yookcalc.base.UiState

data class ParentalUiState(
    val birthDate: String = "",
    val useReducedWorkingHours: Boolean? = null,
    val startDate: String = "",
    val endDate: String = "",
    val useSpouseParentalLeave: Boolean? = null,
    val monthlyPay: String = "",

    val isLoading: Boolean = false,
    val calculationDetails: List<String> = emptyList(),
    val totalCompensation: String = ""
) : UiState