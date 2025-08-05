package com.example.yookcalc.presentation.feature.parental

import com.example.yookcalc.base.UiEvent

sealed interface ParentalEvent : UiEvent {
    data class OnStartDateChanged(val date: String) : ParentalEvent
    data class OnEndDateChanged(val date: String) : ParentalEvent
    object OnCalculateClicked : ParentalEvent
}