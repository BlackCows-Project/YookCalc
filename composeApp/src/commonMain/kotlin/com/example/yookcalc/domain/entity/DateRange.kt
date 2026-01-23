package com.example.yookcalc.domain.entity

import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDate
import kotlinx.datetime.plus
import kotlinx.datetime.until

/**
 * 시작일과 종료일 사이의 기간을 관리하는 데이터 클래스입니다.
 */
data class DateRange(
    val startDate: LocalDate, // 휴가 시작일
    val leaveDays: Int,      // 휴가 일수 (시작일 포함 여부에 따라 계산 방식이 달라질 수 있음)
    val endDate: LocalDate,   // 휴가 종료일
) {

    /**
     * 시작일과 종료일을 입력받아 객체를 생성하는 생성자입니다.
     */
    constructor(
        startDate: LocalDate,
        endDate: LocalDate,
    ): this(startDate, endDate.until(startDate, DateTimeUnit.DAY).toInt(), endDate)

    /**
     * 시작일과 휴가 일수를 입력받아 객체를 생성하는 생성자입니다.
     */
    constructor(
        startDate: LocalDate,
        leaveDays: Int,
    ): this(startDate, leaveDays, startDate.plus(leaveDays, DateTimeUnit.DAY))

    /**
     * 특정 날짜가 이 기간 내에 포함되는지 확인합니다.
     */
    fun contains(date: LocalDate): Boolean {
        return date in startDate..endDate
    }

    /**
     * 다른 DateRange와 기간이 겹치는지(중복되는지) 확인합니다.
     */
    fun isDuplicated(date: DateRange): Boolean {
        val startDate2 = date.startDate
        val endDate2 = date.endDate

        // 네 가지 조건 중 하나라도 만족하면 겹치는 기간이 존재함
        return startDate in startDate2..endDate2 || 
               endDate in startDate2..endDate2 || 
               startDate2 in startDate..endDate || 
               endDate2 in startDate..endDate
    }
}
