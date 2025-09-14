package com.example.yookcalc.domain.entity

import com.example.yookcalc.data.settings.DaysOfLeaveSetting.getDaysOfLeave
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime


data class MaternityLeave(
    val uniqId: String,
    val leaveListBefBirth: List<DateRange>,
    val type: InfantType,
    val birthDate: LocalDate
) {

    // 디폴트값은 출산일을 현재 시간에 출산은 단태아
    constructor(): this(
        "",
        listOf(),
        InfantType.SINGLE,
        Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date
    )

    val totalDaysOfLeaveBefBirth
        get() = leaveListBefBirth.sumOf { it.leaveDays }

    val maxDaysOfLeave
        get() = type.getDaysOfLeave(birthDate)

    enum class InfantType {
        PRETERM, // 미숙아
        MULTIFETAL, // 다태아
        SINGLE; // 단태아

        val minimumAftBirthDaysOfLeave
            get() = when (this) {
                PRETERM,
                SINGLE,
                    -> 45
                MULTIFETAL -> 60
            }

        val governmentRegistryDays
            get() = when (this) {
                PRETERM -> 40
                MULTIFETAL -> 45
                SINGLE -> 30
            }
    }
}