package com.example.yookcalc.domain.entity

data class ParentalInfo(
    val birthDate: String,
    val useReducedWorkingHours: Boolean,
    val startDate: String,
    val endDate: String,
    val useSpouseParentalLeave: Boolean
)