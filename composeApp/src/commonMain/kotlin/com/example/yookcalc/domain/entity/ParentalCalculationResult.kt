package com.example.yookcalc.domain.entity

data class ParentalCalculationResult(
    val totalCompensation: Double,
    val monthlyCompensation: Double,
    val totalDays: Int,
    val calculationDetails: List<String>
)