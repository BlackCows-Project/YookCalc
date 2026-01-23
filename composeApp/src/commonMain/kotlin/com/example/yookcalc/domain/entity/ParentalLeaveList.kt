package com.example.yookcalc.domain.entity

import kotlinx.datetime.LocalDate

data class ParentalLeaveList(
    val uniqId: String,
    val leaveList: List<ParentalLeave>,
    val normalWage: Int,
    val spouseInitialDate: LocalDate = LocalDate(1970, 1, 1),
) {
    fun setSpouseInitialDate(date: LocalDate): ParentalLeaveList = this.copy(spouseInitialDate = date)
}