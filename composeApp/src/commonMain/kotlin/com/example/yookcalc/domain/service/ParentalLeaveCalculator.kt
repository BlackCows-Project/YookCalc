package com.example.yookcalc.domain.service

import com.example.yookcalc.data.settings.ParentalLeavePaySetting
import com.example.yookcalc.domain.entity.DateRange
import com.example.yookcalc.domain.entity.ParentalLeave
import com.example.yookcalc.domain.entity.ParentalLeaveList

class ParentalLeaveCalculator {

    val checkInitialized by lazy {
        ParentalLeavePaySetting.isInitialized
    }

    // (휴가 회차 인덱스, 휴가 내 round)
    // 해당 휴가 회차내 round기준으로 이후는 적용되었고 이전은 소급적용 대상
    private fun checkSixApplyStartRound(parentalLeaveList: ParentalLeaveList): Pair<Int, Int>? = with(parentalLeaveList) {
        if (!checkInitialized) return null

        leaveList.forEachIndexed { idx1, parentalLeave ->
            parentalLeave.rangeList.forEachIndexed { idx2, range ->
                if (range.contains(spouseInitialDate)) {
                    return Pair(idx1, idx2)
                }
            }
        }

        return null
    }

    private fun checkAndAddParentalLeave(parentalLeave: ParentalLeave, parentalLeaveList: ParentalLeaveList): ParentalLeaveList? {
        if (!checkInitialized) return null

        if (parentalLeaveList.leaveList.size >= 4) return null

        parentalLeaveList.leaveList.forEach {
            if (it.range.isDuplicated(parentalLeave.range)) {
                return null
            }
        }

        if (parentalLeave.endRound > 12) return null

        return parentalLeaveList.copy(leaveList = parentalLeaveList.leaveList + parentalLeave)
    }


    private fun initializeParentalLeave(parentalLeaveList: ParentalLeaveList, dateRange: DateRange): ParentalLeave? {
        if (parentalLeaveList.leaveList.size >= 4) return null

        parentalLeaveList.leaveList.lastOrNull()?.let {
            return ParentalLeave(
                dateRange,
                it.endRestDate,
                if (it.endRestDate == 0) it.endRound + 1 else it.endRound,
            )
        }

        return ParentalLeave(
            dateRange,
            0,
            1,
        )
    }
}