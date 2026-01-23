package com.example.yookcalc.data.model

import com.example.yookcalc.domain.entity.MaternityLeave
import kotlinx.serialization.Serializable

@Serializable
data class MaternityLeavePolicy(
    val appliedAt: String,
    val policyType: String,
    val leaveDuration: Int,
    val childBirthType: MaternityLeave.InfantType
)
