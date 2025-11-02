package com.example.yookcalc.domain.entity

import kotlinx.serialization.Serializable

@Serializable
data class MaternityInfo(
    val dueDate: String,
    val startDate: String,
    val endDate: String,
    val hasMultipleBirth: Boolean,
    val hasMiscarriageHistory: Boolean,
    val workStartTime: String = "",
    val workEndTime: String = ""
)


