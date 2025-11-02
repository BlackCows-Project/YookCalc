package com.example.yookcalc.data.model

import com.example.yookcalc.domain.entity.MaternityLeave

data class MaternityLeavePolicy(
    val appliedAt: String,
    val policyType: String,
    val leaveDuration: Int,
    val childBirthType: MaternityLeave.InfantType
)
