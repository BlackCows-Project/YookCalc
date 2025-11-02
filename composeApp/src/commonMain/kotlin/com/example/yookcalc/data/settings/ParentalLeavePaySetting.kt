package com.example.yookcalc.data.settings

import com.example.yookcalc.data.api.ParentalLeavePolicyResponse
import com.example.yookcalc.data.model.ParentalLeavePolicy

object ParentalLeavePaySetting {

    var isInitialized = false

    private val settings: MutableList<ParentalLeaveSetting> = mutableListOf()

    fun setParentalLeavePaySetting(parentalLeavePayPolicy: List<ParentalLeavePolicy>?) {
        if (parentalLeavePayPolicy.isNullOrEmpty()) {
            setDefaultParentalLeavePaySetting()
            return
        }

        isInitialized = true
        settings.clear()

        parentalLeavePayPolicy.forEach {
            val year = it.appliedAt.take(4).toInt()
            val policyType = it.policyType

            ParentalLeaveSetting(policyType, year).apply {
                val policy = it.leavePayPolicy

                for (i in policy.startPayDurationMonth-1 until policy.endPayDurationMonth) {
                    normalWage[i.toInt()] = (policy.payRate * 100).toInt()
                    postPayment[i.toInt()] = (policy.postPayRate * 100).toInt()
                    upperLimit[i.toInt()] = (policy.maxPayMoney * 100).toInt()
                    lowerLimit[i.toInt()] = (policy.minPayMoney * 100).toInt()
                }

                settings.add(this)
            }
        }
    }

    private fun setDefaultParentalLeavePaySetting() {
        val normalSettings = List(3) { ParentalLeaveSetting(ParentalLeavePolicyResponse.PolicyType.NORMAL, 2023 + it) }
        val sixSettings = List(3) { ParentalLeaveSetting(ParentalLeavePolicyResponse.PolicyType.SIX, 2023 + it) }

        for (i in 0..11) {
            normalSettings[0].run {
                normalWage[i] = 80
                postPayment[i] = 75
                upperLimit[i] = 1500000
                lowerLimit[i] = 700000
            }
            normalSettings[1].run {
                normalWage[i] = 80
                postPayment[i] = 75
                upperLimit[i] = 1500000
                lowerLimit[i] = 700000
            }
            normalSettings[2].run {
                normalWage[i] = 80
                postPayment[i] = 75
                upperLimit[i] = if (i < 6) 100 else 80
                lowerLimit[i] = 700000
            }
            sixSettings[0].run {
                normalWage[i] = if (i < 3) 100 else 80
                postPayment[i] = if (i < 3) 100 else 75
                upperLimit[i] = when (i) {
                    0 -> 2000000
                    1 -> 2500000
                    2 -> 3000000
                    else -> 1500000
                }
                lowerLimit[i] = 700000
            }
            sixSettings[1].run {
                normalWage[i] = if (i < 6) 100 else 80
                postPayment[i] = if (i < 6) 100 else 75
                upperLimit[i] = when (i) {
                    in 0..5 -> 2000000 + i * 500000
                    else -> 1500000
                }
                lowerLimit[i] = 700000
            }
            sixSettings[2].run {
                normalWage[i] = if (i < 6) 100 else 80
                postPayment[i] = 100
                upperLimit[i] = when (i) {
                    0, 1 -> 2500000
                    in 2..5 -> 3000000 + i * 500000
                    else -> 1600000
                }
                lowerLimit[i] = 700000
            }
        }
    }

    class ParentalLeaveSetting(val type: ParentalLeavePolicyResponse.PolicyType, val year: Int) {
        val normalWage = MutableList(12) { 0 }
        val postPayment = MutableList(12) { 0 }
        val upperLimit = MutableList(12) { 0 }
        val lowerLimit = MutableList(12) { 0 }
    }
}