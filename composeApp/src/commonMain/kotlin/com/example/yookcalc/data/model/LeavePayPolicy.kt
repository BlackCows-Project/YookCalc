package com.example.yookcalc.data.model

data class LeavePayPolicy(
    val startPayDurationMonth: Long,
    val endPayDurationMonth: Long,
    val maxPayMoney: Long,
    val minPayMoney: Long,
    val payRate: Double,
    val postPayRate: Double,
)
