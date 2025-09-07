package com.example.yookcalc.presentation.feature.maternity

import com.example.yookcalc.base.UiState

data class MaternityUiState(
    val dueDate: String = "2025.06.16",
    val startDate: String = "2025.06.16",
    val endDate: String = "2025.09.13",
    val hasMultipleBirth: Boolean? = null,
    val hasMiscarriageHistory: Boolean? = null,
    val workStart: String = "",
    val workEnd: String = "",
    val first30DaysPay: String = "",
    val second30DaysPay: String = "",
    val third30DaysPay: String = "",
    val compensation: String = "",

    val isLoading: Boolean = false,
    val calculationDetails: List<String> = emptyList()
) : UiState