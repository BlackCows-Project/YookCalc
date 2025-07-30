package com.example.yookcalc.presentation.feature.mypage

import com.example.yookcalc.base.UiEvent


sealed interface MyPageEvent : UiEvent {
    object LoadDefaultData : MyPageEvent // 화면 처음 들어올 때 기본 데이터 로드
    object NavigateToSetting : MyPageEvent // 설명 화면 이동 이벤트
}