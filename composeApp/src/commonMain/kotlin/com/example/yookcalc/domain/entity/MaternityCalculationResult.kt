package com.example.yookcalc.domain.entity

import kotlinx.serialization.Serializable

@Serializable
data class MaternityCalculationResult(
    val totalDays: Int,
    val first30DaysPay: Double,
    val second30DaysPay: Double,
    val third30DaysPay: Double,
    val totalCompensation: Double,
    val calculationDetails: List<String>
)