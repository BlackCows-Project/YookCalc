package com.example.yookcalc.presentation.feature.shortened_work

import com.example.yookcalc.base.UiEvent

sealed interface ShortenedWorkEvent : UiEvent {
    data class OnWorkHoursChanged(val hours: String) : ShortenedWorkEvent
    object OnCalculateClicked : ShortenedWorkEvent
}