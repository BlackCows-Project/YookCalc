package com.example.yookcalc.domain.entity

import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.plus
import kotlinx.datetime.until
import kotlin.properties.Delegates

// 각 휴가별 데이터
// 상위 데이터 클래스에서 uniqId 들고있음
data class ParentalLeave(
    val range: DateRange,
    val prevRestDays: Int,
    val startRound: Int,
) {

    var endRestDate by Delegates.notNull<Int>()
        private set
    var maxRounds by Delegates.notNull<Int>()
        private set

    val rangeList: MutableList<DateRange> = mutableListOf()

    val payList: MutableList<ParentalPay> = mutableListOf()

    init {
        initialCalculateProperties()
    }

    val endRound: Int
        get() = startRound + maxRounds

    private fun initialCalculateProperties() {
        var rounds = 0
        val initialDate = range.startDate.plus(prevRestDays, DateTimeUnit.DAY)

        rangeList += range.copy(leaveDays = prevRestDays)

        var tmpDate = initialDate

        while (tmpDate < range.endDate) {
            ++rounds
            tmpDate = initialDate.plus(rounds, DateTimeUnit.MONTH)
            rangeList.lastOrNull()?.let {
                rangeList += DateRange(it.startDate.plus(1, DateTimeUnit.DAY), tmpDate)
            }
        }

        rangeList.removeLastOrNull()

        tmpDate = initialDate.plus(--rounds, DateTimeUnit.MONTH)

        endRestDate = range.endDate.until(tmpDate, DateTimeUnit.DAY)
        maxRounds = if (endRestDate == 0) rounds else rounds + 1

        rangeList.lastOrNull()?.let {
            rangeList += DateRange(it.startDate.plus(1, DateTimeUnit.DAY), range.endDate)
        }
    }

    // TODO("급여 계산 - ParentalPay사용")
    fun evaluatePayList(normalWage: Int) {

    }
}
