package com.example.yookcalc.domain.entity

import kotlinx.serialization.Serializable

@Serializable
data class UserSalaryInfo(
    val monthlyPay: Double,
    val dailyPay: Double,
    val baseSalary: Double
)