package com.example.yookcalc.data.api

import com.example.yookcalc.data.model.ParentalLeavePolicy
import kotlinx.serialization.SerialName

data class ParentalLeavePolicyResponse(
    @SerialName("appliedAt") val appliedAt: String? = null,
    @SerialName("policyType") val policyType: String? = null,
    @SerialName("leavePayPolicy") val leavePayPolicy: ParentalLeavePayPolicyResponse? = null,
) {
    enum class PolicyType(val value: String) {
        NORMAL("normal"),
        SIX("six");
    }

    fun toParentalLeavePolicy(): ParentalLeavePolicy? {
        return runCatching {
            ParentalLeavePolicy(
                appliedAt = appliedAt ?: return null,
                policyType = PolicyType.valueOf(policyType ?: return null),
                leavePayPolicy = leavePayPolicy?.toLeavePayPolicy() ?: return null,
            )
        }.getOrNull()
    }
}