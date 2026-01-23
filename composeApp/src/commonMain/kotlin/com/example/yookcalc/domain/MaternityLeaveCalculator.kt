package com.example.yookcalc.domain

import kotlinx.datetime.*

/**
 * 출산 휴가 계산을 담당하는 순수 비즈니스 로직 클래스입니다.
 * A class responsible for the pure business logic of calculating maternity leave.
 */
class MaternityLeaveCalculator {

    /**
     * 출산 유형 (단태아, 다태아, 미숙아 등)
     * Enum for birth type (single, multiple, premature, etc.).
     */
    enum class BirthType(val totalLeaveDays: Int, val requiredPostpartumDays: Int) {
        SINGLE(90, 45),
        MULTIPLE(120, 60),
        PREMATURE(100, 45) // Based on the Excel file, can be adjusted
    }

    /**
     * 계산 결과를 담는 데이터 클래스입니다.
     * Data class to hold the calculation result.
     */
    data class LeaveCalculationResult(
        val endDate: LocalDate,
        val postpartumDaysSecured: Long,
        val isPostpartumMet: Boolean,
        val requiredDays: Int,
        val maxPrenatalStartDate: LocalDate
    )

    /**
     * 날짜 문자열을 LocalDate 객체로 변환합니다.
     * Converts a date string to a LocalDate object.
     */
    private fun parseDate(dateStr: String): LocalDate? {
        return try {
            LocalDate.parse(dateStr)
        } catch (e: Exception) {
            null
        }
    }

    /**
     * 출산 휴가 정보를 계산합니다.
     * Calculates the maternity leave information.
     */
    fun calculate(
        startDateStr: String,
        dueDateStr: String,
        birthType: BirthType
    ): Result<LeaveCalculationResult> {
        val startDate = parseDate(startDateStr)
        val dueDate = parseDate(dueDateStr)

        if (startDate == null || dueDate == null) {
            return Result.failure(Exception("올바른 날짜 형식이 아닙니다. (YYYY-MM-DD)"))
        }

        val endDate = startDate.plus(birthType.totalLeaveDays - 1, DateTimeUnit.DAY)
        val postpartumDaysSecured = dueDate.daysUntil(endDate)
        val isPostpartumMet = postpartumDaysSecured >= birthType.requiredPostpartumDays
        val maxPrenatalStartDate = dueDate.minus(birthType.totalLeaveDays - birthType.requiredPostpartumDays, DateTimeUnit.DAY)


        return Result.success(
            LeaveCalculationResult(
                endDate = endDate,
                postpartumDaysSecured = postpartumDaysSecured.toLong(),
                isPostpartumMet = isPostpartumMet,
                requiredDays = birthType.requiredPostpartumDays,
                maxPrenatalStartDate = maxPrenatalStartDate
            )
        )
    }
}