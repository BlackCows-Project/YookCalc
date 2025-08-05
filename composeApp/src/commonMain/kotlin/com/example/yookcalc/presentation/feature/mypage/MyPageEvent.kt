package com.example.yookcalc.presentation.feature.mypage

import com.example.yookcalc.base.UiEvent

sealed interface MyPageEvent : UiEvent {
    object OnProfileClicked : MyPageEvent
    object OnSettingClicked : MyPageEvent // ← 추가
}