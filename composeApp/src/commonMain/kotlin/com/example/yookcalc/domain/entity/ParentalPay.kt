package com.example.yookcalc.domain.entity

data class ParentalPay(
    val restIdx: Int,
    val round: Int,
    val normal: Int,
    val post: Int,
    val additional: Int = 0,
)
