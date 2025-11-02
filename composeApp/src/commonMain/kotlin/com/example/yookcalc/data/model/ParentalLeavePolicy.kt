package com.example.yookcalc.data.model

import com.example.yookcalc.data.api.ParentalLeavePolicyResponse
import kotlinx.serialization.Serializable

@Serializable
data class ParentalLeavePolicy(
    val appliedAt: String,
    val policyType: ParentalLeavePolicyResponse.PolicyType,
    val leavePayPolicy: LeavePayPolicy,
)
