package com.example.yookcalc.presentation.feature.maternity

import com.example.yookcalc.base.UiEvent

sealed interface MaternityEvent : UiEvent {
    data class OnStartDateChanged(val date: String) : MaternityEvent
    data class OnEndDateChanged(val date: String) : MaternityEvent
    object OnCalculateClicked : MaternityEvent
}