package com.example.yookcalc.presentation.feature.mypage

import com.example.yookcalc.base.UiEffect

sealed interface MyPageEffect : UiEffect {
    data class ShowToast(val message: String) : MyPageEffect
    object NavigateToSetting : MyPageEffect // ← 추가
}
