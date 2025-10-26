package com.example.yookcalc.presentation.feature.parental

import com.example.yookcalc.base.UiEvent

sealed interface ParentalEvent : UiEvent {
    object LoadDefaultData : ParentalEvent
    data class ChangeBirthDate(val value: String) : ParentalEvent
    data class SelectReducedWorkingHours(val value: Boolean) : ParentalEvent
    data class ChangeStartDate(val value: String) : ParentalEvent
    data class ChangeEndDate(val value: String) : ParentalEvent
    data class SelectSpouseParentalLeave(val value: Boolean) : ParentalEvent
    data class ChangeMonthlyPay(val value: String) : ParentalEvent
    object CalculateParentalPay : ParentalEvent
    object SaveCurrentInfo : ParentalEvent
}