package com.example.yookcalc.data.api

import com.example.yookcalc.data.model.MaternityLeavePolicy
import com.example.yookcalc.domain.entity.MaternityLeave
import kotlinx.serialization.SerialName

data class MaternityLeavePolicyResponse(
    @SerialName("appliedAt") val appliedAt: String?,
    @SerialName("policyType") val policyType: String?,
    @SerialName("leaveDuration") val leaveDuration: Int?,
    @SerialName("policyCondition") val policyCondition: PolicyCondition?,
) {
    data class PolicyCondition(
        @SerialName("childBirthType") val childBirthType: MaternityLeave.InfantType?
    )

    fun toMaternityLeavePolicy(): MaternityLeavePolicy? {
        return MaternityLeavePolicy(
            appliedAt = appliedAt ?: return null,
            policyType = policyType ?: return null,
            leaveDuration = leaveDuration ?: return null,
            childBirthType = policyCondition?.childBirthType ?: return null,
        )
    }
}
