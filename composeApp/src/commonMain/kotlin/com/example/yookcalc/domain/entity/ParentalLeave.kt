package com.example.yookcalc.domain.entity

import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.plus
import kotlinx.datetime.until
import kotlin.properties.Delegates

// 각 개별 휴가(분할 사용 시)에 대한 데이터와 계산 로직을 담고 있습니다.
data class ParentalLeave(
    val range: DateRange,    // 전체 휴가 기간
    val prevRestDays: Int,   // 이전 회차에서 남은 일수 (월 단위로 끊고 남은 일수)
    val startRound: Int,     // 이 휴가 회차가 시작되는 라운드 번호 (예: 1개월차, 2개월차...)
) {

    // 현재 휴가 기간이 끝나고 남은 일수 (다음 회차로 넘겨줄 데이터)
    var endRestDate by Delegates.notNull<Int>()
        private set
    
    // 이 휴가 기간 동안 총 몇 개월(라운드)이 포함되는지
    var maxRounds by Delegates.notNull<Int>()
        private set

    // 전체 기간을 1개월 단위로 쪼갠 리스트
    val rangeList: MutableList<DateRange> = mutableListOf()

    // 각 라운드별 급여 정보 리스트
    val payList: MutableList<ParentalPay> = mutableListOf()

    init {
        // 객체 생성 시 자동으로 기간 및 라운드 계산 수행
        initialCalculateProperties()
    }

    // 이 휴가가 끝나는 시점의 라운드 번호
    val endRound: Int
        get() = startRound + maxRounds

    /**
     * 전체 휴가 기간을 한 달 단위의 라운드로 쪼개고, 
     * 남는 일수를 계산하는 핵심 로직입니다.
     */
    private fun initialCalculateProperties() {
        var rounds = 0
        // 이전 회차에서 남은 일수만큼 밀려서 실제 계산 시작일이 정해짐
        val initialDate = range.startDate.plus(prevRestDays, DateTimeUnit.DAY)

        // 첫 번째 기간 추가 (이전 RestDays 포함)
        rangeList += range.copy(leaveDays = prevRestDays)

        var tmpDate = initialDate

        // 휴가 종료일까지 1개월씩 더해가며 라운드 생성
        while (tmpDate < range.endDate) {
            ++rounds
            tmpDate = initialDate.plus(rounds, DateTimeUnit.MONTH)
            rangeList.lastOrNull()?.let {
                // 이전 종료일 + 1일 부터 새로운 1개월 종료일까지를 하나의 리스트 아이템으로 추가
                rangeList += DateRange(it.startDate.plus(1, DateTimeUnit.DAY), tmpDate)
            }
        }

        // 마지막에 추가된 기간이 종료일을 넘었을 수 있으므로 제거 후 보정
        rangeList.removeLastOrNull()

        tmpDate = initialDate.plus(--rounds, DateTimeUnit.MONTH)

        // 종료일 기준으로 정확히 몇 일이 남았는지 계산
        endRestDate = range.endDate.until(tmpDate, DateTimeUnit.DAY).toInt()
        
        // 딱 맞게 떨어지지 않으면 라운드를 하나 추가함
        maxRounds = if (endRestDate == 0) rounds else rounds + 1

        // 마지막 짜투리 기간을 rangeList에 추가
        rangeList.lastOrNull()?.let {
            rangeList += DateRange(it.startDate.plus(1, DateTimeUnit.DAY), range.endDate)
        }
    }

    /**
     * 상세 급여 계산 로직 (현재 미구현)
     * TODO: 통상임금(normalWage)을 기반으로 ParentalPay 리스트를 생성해야 함
     */
    fun evaluatePayList(normalWage: Int) {
        // TODO("급여 계산 - ParentalPay사용")
    }
}

