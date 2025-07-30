package com.example.yookcalc.presentation.screen.maternity

import com.example.yookcalc.presentation.base.UiEvent

// 출산휴직 화면에서 발생할 있는 모든 사용자 액션
// 새로운 버튼이나 입력창이 생기면 이벤트 추가
sealed interface MaternityEvent : UiEvent {
    object LoadDefaultData : MaternityEvent // 화면 처음 들어올 때 기본 데이터 로드
    data class ChangeDueDate(val value: String) : MaternityEvent // 날짜 입력 바꿀 때
    data class ChangeStartDate(val value: String) : MaternityEvent
    data class ChangeEndDate(val value: String) : MaternityEvent
    data class SelectMultipleBirth(val value: Boolean) : MaternityEvent // 버튼 클릭 시
    data class SelectMiscarriage(val value: Boolean) : MaternityEvent
}