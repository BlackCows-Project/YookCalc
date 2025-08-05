package com.example.yookcalc.presentation.feature.shortened_work

import com.example.yookcalc.base.BaseViewModel

class ShortenedWorkViewModel :
    BaseViewModel<ShortenedWorkState, ShortenedWorkEvent, ShortenedWorkEffect>(ShortenedWorkState()) {

    override fun onEvent(event: ShortenedWorkEvent) {
        println("[ShortenedWorkViewModel] Event: $event")
        when (event) {
            is ShortenedWorkEvent.OnWorkHoursChanged -> setState { copy(workHours = event.hours) }
            is ShortenedWorkEvent.OnCalculateClicked -> calculate()
        }
    }

    private fun calculate() {
        val result = "단축근로 ${state.value.workHours} 시간 계산 완료"
        setState { copy(result = result) }
        sendEffect(ShortenedWorkEffect.ShowToast("계산 완료!"))
    }
}