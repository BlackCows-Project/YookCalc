package com.example.yookcalc.presentation.feature.maternity

import com.example.yookcalc.base.BaseViewModel

class MaternityViewModel :
    BaseViewModel<MaternityUiState, MaternityEvent, MaternityEffect>(MaternityUiState()) {

    override fun onEvent(event: MaternityEvent) {
        println("[ViewModel] 이벤트 발생: $event")
        when (event) {
            is MaternityEvent.LoadDefaultData -> loadDefaults()
            is MaternityEvent.ChangeDueDate ->
                setState { copy(dueDate = event.value) }

            is MaternityEvent.ChangeStartDate ->
                setState { copy(startDate = event.value) }

            is MaternityEvent.ChangeEndDate ->
                setState { copy(endDate = event.value) }

            is MaternityEvent.SelectMultipleBirth ->
                setState { copy(hasMultipleBirth = event.value) }

            is MaternityEvent.SelectMiscarriage ->
                setState { copy(hasMiscarriageHistory = event.value) }
        }
    }

    private fun loadDefaults() {
        println("[ViewModel] 기본 데이터 로드 실행")
        sendEffect(MaternityEffect.ShowToast("기본 데이터 불러옴"))
    }
}