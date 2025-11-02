package com.example.yookcalc.data.api

import com.example.yookcalc.data.model.LeavePayPolicy
import kotlinx.serialization.SerialName

data class ParentalLeavePayPolicyResponse(
    @SerialName("startPayDurationMonth") val startPayDurationMonth: Long? = null,
    @SerialName("endPayDurationMonth") val endPayDurationMonth: Long? = null,
    @SerialName("maxPayMoney") val maxPayMoney: Long? = null,
    @SerialName("minPayMoney") val minPayMoney: Long? = null,
    @SerialName("payRate") val payRate: Double? = null,
    @SerialName("postPayRate")  val postPayRate: Double? = null,
) {
    fun toLeavePayPolicy(): LeavePayPolicy? {
        return LeavePayPolicy(
            startPayDurationMonth = startPayDurationMonth ?: return null,
            endPayDurationMonth = endPayDurationMonth ?: return null,
            maxPayMoney = maxPayMoney ?: return null,
            minPayMoney = minPayMoney ?: return null,
            payRate = payRate ?: return null,
            postPayRate = postPayRate ?: return null
        )
    }
}