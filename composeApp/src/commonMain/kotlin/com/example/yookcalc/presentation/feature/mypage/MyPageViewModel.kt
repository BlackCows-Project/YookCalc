package com.example.yookcalc.presentation.feature.mypage

import com.example.yookcalc.base.BaseViewModel

class MyPageViewModel :
    BaseViewModel<MyPageState, MyPageEvent, MyPageEffect>(MyPageState()) {

    override fun onEvent(event: MyPageEvent) {
        println("[MyPageViewModel] Event: $event")
        when (event) {
            MyPageEvent.OnProfileClicked -> {
                setState { copy(message = "프로필 클릭됨") }
                sendEffect(MyPageEffect.ShowToast("프로필 화면 이동 예정"))
            }
            MyPageEvent.OnSettingClicked -> {
                sendEffect(MyPageEffect.NavigateToSetting) // ← Effect 전송
            }
        }
    }
}