package com.example.yookcalc.presentation.feature.maternity

import com.example.yookcalc.base.UiEvent

sealed interface MaternityEvent : UiEvent {
    object LoadDefaultData : MaternityEvent // 화면 처음 들어올 때 기본 데이터 로드
    data class ChangeDueDate(val value: String) : MaternityEvent // 날짜 입력 바꿀 때
    data class ChangeStartDate(val value: String) : MaternityEvent
    data class ChangeEndDate(val value: String) : MaternityEvent
    data class SelectMultipleBirth(val value: Boolean) : MaternityEvent // 버튼 클릭 시
    data class SelectMiscarriage(val value: Boolean) : MaternityEvent
    object CalculateMaternityPay : MaternityEvent
    object SaveCurrentInfo : MaternityEvent
}