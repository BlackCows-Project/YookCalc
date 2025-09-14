package com.example.yookcalc.domain.entity

import kotlinx.datetime.LocalDate

data class DaysOfMaternityLeave(
    val type: MaternityLeave.InfantType,
    val applyDate: LocalDate,
    val daysOfLeave: Int,
)
