package com.example.yookcalc.presentation.feature.maternity

import com.example.yookcalc.base.BaseViewModel

class MaternityViewModel :
    BaseViewModel<MaternityState, MaternityEvent, MaternityEffect>(MaternityState()) {

    override fun onEvent(event: MaternityEvent) {
        println("[MaternityViewModel] Event: $event")
        when (event) {
            is MaternityEvent.OnStartDateChanged -> setState { copy(startDate = event.date) }
            is MaternityEvent.OnEndDateChanged -> setState { copy(endDate = event.date) }
            is MaternityEvent.OnCalculateClicked -> calculateLeavePeriod()
        }
    }

    private fun calculateLeavePeriod() {
        val result = "${state.value.startDate} ~ ${state.value.endDate} 출산휴가 계산 완료"
        setState { copy(result = result) }
        sendEffect(MaternityEffect.ShowToast("계산 완료!"))
    }
}