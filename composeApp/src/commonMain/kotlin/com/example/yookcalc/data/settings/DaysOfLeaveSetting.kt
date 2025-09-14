package com.example.yookcalc.data.settings

import com.example.yookcalc.domain.entity.DaysOfMaternityLeave
import com.example.yookcalc.domain.entity.MaternityLeave
import com.example.yookcalc.domain.entity.MaternityLeave.InfantType.MULTIFETAL
import com.example.yookcalc.domain.entity.MaternityLeave.InfantType.PRETERM
import com.example.yookcalc.domain.entity.MaternityLeave.InfantType.SINGLE
import kotlinx.datetime.LocalDate

object DaysOfLeaveSetting {
    private val pretermDivider: MutableList<Pair<LocalDate, Int>> = mutableListOf()
    private val multifetalDivider: MutableList<Pair<LocalDate, Int>> = mutableListOf()
    private val singleDivider: MutableList<Pair<LocalDate, Int>> = mutableListOf()

    var isInitialized = false

    // 날짜순으로 추가되도록 정렬
    fun setDaysOfLeave(dividerList: List<DaysOfMaternityLeave>) {
        dividerList.sortedBy { it.applyDate }.forEach {
            when (it.type) {
                PRETERM -> pretermDivider.add(it.applyDate to it.daysOfLeave)
                MULTIFETAL -> multifetalDivider.add(it.applyDate to it.daysOfLeave)
                SINGLE -> singleDivider.add(it.applyDate to it.daysOfLeave)
            }
        }

        isInitialized = true
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

        isInitialized = true
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