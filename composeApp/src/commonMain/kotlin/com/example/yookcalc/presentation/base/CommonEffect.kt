package com.example.yookcalc.presentation.base

sealed interface CommonEffect : UiEffect {
    data class ShowToast(val message: String) : CommonEffect
    object NavigateBack : CommonEffect
    data class NavigateTo(val route: String) : CommonEffect
}
