package com.example.yookcalc.domain.entity

import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDate
import kotlinx.datetime.plus
import kotlinx.datetime.until

data class DateRange(
    val startDate: LocalDate,
    val leaveDays: Int,
    val endDate: LocalDate,
) {

    constructor(
        startDate: LocalDate,
        endDate: LocalDate,
    ): this(startDate, endDate.until(startDate, DateTimeUnit.DAY), endDate)

    constructor(
        startDate: LocalDate,
        leaveDays: Int,
    ): this(startDate, leaveDays, startDate.plus(leaveDays, DateTimeUnit.DAY))

    fun contains(date: LocalDate): Boolean {
        return date in startDate..endDate
    }

    fun isDuplicated(date: DateRange): Boolean {
        val startDate2 = date.startDate
        val endDate2 = date.endDate

        return startDate in startDate2..endDate2 || endDate in startDate2..endDate2 || startDate2 in startDate..endDate || endDate2 in startDate..endDate
    }
}