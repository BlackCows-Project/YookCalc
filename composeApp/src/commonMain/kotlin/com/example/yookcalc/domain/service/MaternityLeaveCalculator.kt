package com.example.yookcalc.domain.service

import com.example.yookcalc.data.settings.DaysOfLeaveSetting
import com.example.yookcalc.domain.entity.DateRange
import com.example.yookcalc.domain.entity.MaternityLeave
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDate
import kotlinx.datetime.minus
import kotlinx.datetime.plus

class MaternityLeaveCalculator {

    val checkInitialized by lazy {
        DaysOfLeaveSetting.isInitialized
    }

    // 0이상이면 산전휴가 추가 가능, 음수면 산후휴가일수 확보를 위해 해당 일수만큼 산전 휴가일 줄여야 함.
    fun checkLeaveIsValid(dateRange: DateRange, maternityLeave: MaternityLeave): Int? {
        if (!checkInitialized) return null

        val restDaysLeaveBefBirth = maternityLeave.let {
            it.maxDaysOfLeave - it.totalDaysOfLeaveBefBirth - it.type.minimumAftBirthDaysOfLeave
        }

        return restDaysLeaveBefBirth - dateRange.leaveDays
    }

    // 대규모 기업 정부지원분 신청 시작 기간
    fun checkGovernmentRegistry(maternityLeave: MaternityLeave): LocalDate? {
        if (!checkInitialized) return null

        val daysOfLeaveAftBirth = maternityLeave.let {
            it.maxDaysOfLeave - it.totalDaysOfLeaveBefBirth
        }

        val registryDay = daysOfLeaveAftBirth - maternityLeave.type.governmentRegistryDays

        return maternityLeave.birthDate.apply {
            plus(registryDay, DateTimeUnit.DAY)
        }
    }

    // 산전 휴가 시작 가능일
    fun validStartLeaveDate(maternityLeave: MaternityLeave): LocalDate? {
        if (!checkInitialized) return null

        return maternityLeave.let {
            it.birthDate.minus(it.maxDaysOfLeave - it.type.minimumAftBirthDaysOfLeave, DateTimeUnit.DAY)
        }
    }
}