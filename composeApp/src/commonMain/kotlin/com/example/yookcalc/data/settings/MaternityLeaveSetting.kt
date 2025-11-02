package com.example.yookcalc.data.settings

import com.example.yookcalc.data.model.MaternityLeavePolicy
import com.example.yookcalc.domain.entity.MaternityLeave
import com.example.yookcalc.domain.entity.MaternityLeave.InfantType.MULTIFETAL
import com.example.yookcalc.domain.entity.MaternityLeave.InfantType.PRETERM
import com.example.yookcalc.domain.entity.MaternityLeave.InfantType.SINGLE
import kotlinx.datetime.LocalDate

object MaternityLeaveSetting {
    private val pretermDivider: MutableList<Pair<LocalDate, Int>> = mutableListOf()
    private val multifetalDivider: MutableList<Pair<LocalDate, Int>> = mutableListOf()
    private val singleDivider: MutableList<Pair<LocalDate, Int>> = mutableListOf()

    var isInitialized = false

    // 날짜순으로 추가되도록 정렬
    fun setDaysOfLeave(maternityLeavePolicy: List<MaternityLeavePolicy>?) {
        if (maternityLeavePolicy.isNullOrEmpty()) {
            setDefaultDaysOfLeave()
            return
        }

        isInitialized = true
        pretermDivider.clear()
        multifetalDivider.clear()
        singleDivider.clear()

        maternityLeavePolicy.forEach {
            val format = LocalDate.Format {
                year()
                monthNumber()
                dayOfMonth()
            }
            val date = format.parse(it.appliedAt)

            when (it.childBirthType) {
                PRETERM -> pretermDivider
                MULTIFETAL -> multifetalDivider
                SINGLE -> singleDivider
            }.apply {
                add(Pair(date, it.leaveDuration))
            }
        }
    }

    fun setDefaultDaysOfLeave() {
        pretermDivider.addAll(
            listOf(
                LocalDate(1970, 1, 1) to 90,
                LocalDate(2025, 2, 22) to 100,
            )
        )
        multifetalDivider.add(
            LocalDate(1970, 1, 1) to 120,
        )
        singleDivider.add(
            LocalDate(1970, 1, 1) to 90,
        )
    }

    fun MaternityLeave.InfantType.getDaysOfLeave(date: LocalDate): Int {
        var currentDivider: LocalDate? = null
        var daysOfLeave: Int = 0
        when (this) {
            PRETERM -> pretermDivider
            MULTIFETAL -> multifetalDivider
            SINGLE -> singleDivider
        }.forEach {
            val postDivider = it.first
            daysOfLeave = it.second

            currentDivider?.let { prevDivider ->
                if (date in prevDivider ..< postDivider) {
                    return@forEach
                }
            } ?: run {
                currentDivider = postDivider
            }
        }

        return daysOfLeave
    }
}