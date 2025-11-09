package com.example.yookcalc.data.api

import com.example.yookcalc.data.model.MaternityLeavePolicy
import com.example.yookcalc.domain.entity.MaternityLeave
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class MaternityLeavePolicyResponse(
    @SerialName("appliedAt") val appliedAt: String?,
    @SerialName("policyType") val policyType: String?,
    @SerialName("leaveDuration") val leaveDuration: Int?,
    @SerialName("policyCondition") val policyCondition: PolicyCondition?,
) {
    @Serializable
    data class PolicyCondition(
        @SerialName("childBirthType") val childBirthType: String?
    )

    fun toMaternityLeavePolicy(): MaternityLeavePolicy? {
        return MaternityLeavePolicy(
            appliedAt = appliedAt ?: return null,
            policyType = policyType ?: return null,
            leaveDuration = leaveDuration ?: return null,
            childBirthType = policyCondition?.childBirthType?.let { MaternityLeave.InfantType.fromValue(it) } ?: return null,
        )
    }
}
