package com.example.yookcalc.presentation.screen.maternity

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MaternityViewModel {
    private val viewModelScope = CoroutineScope(SupervisorJob() + Dispatchers.Main)

    private val _uiState = MutableStateFlow(MaternityUiState())
    val uiState: StateFlow<MaternityUiState> = _uiState.asStateFlow()

    private val _effect = MutableSharedFlow<MaternityEffect>()
    val effect: SharedFlow<MaternityEffect> = _effect.asSharedFlow()

    fun onEvent(event: MaternityEvent) {
        println("[ViewModel] 이벤트 발생: $event")
        when (event) {
            is MaternityEvent.LoadDefaultData -> loadDefaults()
            is MaternityEvent.ChangeDueDate ->
                _uiState.update { it.copy(dueDate = event.value) }
            is MaternityEvent.ChangeStartDate ->
                _uiState.update { it.copy(startDate = event.value) }
            is MaternityEvent.ChangeEndDate ->
                _uiState.update { it.copy(endDate = event.value) }
            is MaternityEvent.SelectMultipleBirth ->
                _uiState.update { it.copy(hasMultipleBirth = event.value) }
            is MaternityEvent.SelectMiscarriage ->
                _uiState.update { it.copy(hasMiscarriageHistory = event.value) }
        }
    }

    private fun loadDefaults() {
        println("[ViewModel] 기본 데이터 로드 실행")
        viewModelScope.launch {
            _effect.emit(MaternityEffect.ShowToast("기본 데이터 불러옴"))
        }
    }
}