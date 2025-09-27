package com.example.yookcalc.presentation

import com.example.yookcalc.domain.MaternityLeaveCalculator
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

/**
 * MaternityLeaveScreen의 UI 상태를 정의하는 데이터 클래스입니다.
 * Data class that defines the UI state for MaternityLeaveScreen.
 */
data class MaternityLeaveUiState(
    val startDate: String = "",
    val dueDate: String = "",
    val birthType: MaternityLeaveCalculator.BirthType = MaternityLeaveCalculator.BirthType.SINGLE,
    val result: MaternityLeaveCalculator.LeaveCalculationResult? = null,
    val error: String? = null
)

/**
 * MaternityLeaveScreen의 상태 관리와 비즈니스 로직을 처리하는 ViewModel입니다.
 * The ViewModel that manages state and handles business logic for MaternityLeaveScreen.
 */
class MaternityLeaveViewModel {

    private val calculator = MaternityLeaveCalculator()

    private val _uiState = MutableStateFlow(MaternityLeaveUiState())
    val uiState = _uiState.asStateFlow()

    /**
     * 사용자가 휴가 시작일을 변경했을 때 호출됩니다.
     * Called when the user changes the leave start date.
     */
    fun updateStartDate(date: String) {
        _uiState.update { it.copy(startDate = date) }
        calculate()
    }

    /**
     * 사용자가 출산(예정)일을 변경했을 때 호출됩니다.
     * Called when the user changes the due date.
     */
    fun updateDueDate(date: String) {
        _uiState.update { it.copy(dueDate = date) }
        calculate()
    }

    /**
     * 사용자가 출산 유형을 변경했을 때 호출됩니다.
     * Called when the user changes the birth type.
     */
    fun updateBirthType(type: MaternityLeaveCalculator.BirthType) {
        _uiState.update { it.copy(birthType = type) }
        calculate()
    }

    /**
     * 현재 UI 상태를 기반으로 휴가 기간을 계산하고, 결과를 State에 반영합니다.
     * Calculates the leave period based on the current UI state and updates the state with the result.
     */
    private fun calculate() {
        val currentState = _uiState.value
        // 두 날짜 필드가 모두 비어있지 않을 때만 계산을 시도합니다.
        // Attempt calculation only when both date fields are not blank.
        if (currentState.startDate.isNotBlank() && currentState.dueDate.isNotBlank()) {
            val result = calculator.calculate(
                startDateStr = currentState.startDate,
                dueDateStr = currentState.dueDate,
                birthType = currentState.birthType
            )

            result.onSuccess { calculationResult ->
                _uiState.update {
                    it.copy(result = calculationResult, error = null)
                }
            }.onFailure { exception ->
                _uiState.update {
                    it.copy(result = null, error = exception.message)
                }
            }
        } else {
            // 필드가 비어있다면 이전 계산 결과나 에러 메시지를 초기화합니다.
            // If fields are empty, clear previous results or error messages.
            _uiState.update { it.copy(result = null, error = null) }
        }
    }
}