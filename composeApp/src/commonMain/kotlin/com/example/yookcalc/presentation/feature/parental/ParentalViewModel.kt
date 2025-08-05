package com.example.yookcalc.presentation.feature.parental

import androidx.lifecycle.ViewModel
import com.example.yookcalc.base.BaseViewModel
import kotlinx.coroutines.launch

class ParentalViewModel :
    BaseViewModel<ParentalState, ParentalEvent, ParentalEffect>(ParentalState()) {

    override fun onEvent(event: ParentalEvent) {
        println("[ParentalViewModel] Event: $event")
        when (event) {
            is ParentalEvent.OnStartDateChanged -> setState { copy(startDate = event.date) }
            is ParentalEvent.OnEndDateChanged -> setState { copy(endDate = event.date) }
            is ParentalEvent.OnCalculateClicked -> calculateLeavePeriod()
        }
    }

    private fun calculateLeavePeriod() {
        val result = "${state.value.startDate} ~ ${state.value.endDate} 기간 계산됨"
        setState { copy(result = result) }
        sendEffect(ParentalEffect.ShowToast("계산 완료!"))
    }
}
