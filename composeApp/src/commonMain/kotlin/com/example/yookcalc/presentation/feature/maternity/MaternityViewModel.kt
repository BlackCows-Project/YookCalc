package com.example.yookcalc.presentation.feature.maternity

import com.example.yookcalc.base.BaseViewModel
import com.example.yookcalc.domain.entity.MaternityInfo
import com.example.yookcalc.domain.usecase.CalculateMaternityPayUseCase
import com.example.yookcalc.domain.usecase.GetMaternityInfoUseCase
import com.example.yookcalc.domain.usecase.GetUserSalaryInfoUseCase
import com.example.yookcalc.domain.usecase.SaveMaternityInfoUseCase
import kotlinx.coroutines.launch

class MaternityViewModel(private val getMaternityInfoUseCase: GetMaternityInfoUseCase,
                         private val saveMaternityInfoUseCase: SaveMaternityInfoUseCase,
                         private val calculateMaternityPayUseCase: CalculateMaternityPayUseCase,
                         private val getUserSalaryInfoUseCase: GetUserSalaryInfoUseCase
) : BaseViewModel<MaternityUiState, MaternityEvent, MaternityEffect>(MaternityUiState()) {
    override fun onEvent(event: MaternityEvent) {
        println("[ViewModel] 이벤트 발생: $event")
        when (event) {
            is MaternityEvent.LoadDefaultData -> loadDefaults()
            is MaternityEvent.ChangeDueDate -> updateDueDate(event.value)
            is MaternityEvent.ChangeStartDate -> updateStartDate(event.value)
            is MaternityEvent.ChangeEndDate -> updateEndDate(event.value)
            is MaternityEvent.SelectMultipleBirth -> updateMultipleBirth(event.value)
            is MaternityEvent.SelectMiscarriage -> updateMiscarriage(event.value)
            is MaternityEvent.CalculateMaternityPay -> calculatePay()
            is MaternityEvent.SaveCurrentInfo -> saveCurrentInfo()
        }
    }

    private fun loadDefaults() {
        viewModelScope.launch {
            println("[ViewModel] 기본 데이터 로드 시작")

            // 저장된 출산휴직 정보 불러오기
            getMaternityInfoUseCase()
                .onSuccess { info ->
                    println("[ViewModel] 출산휴직 정보 로드 성공: $info")
                    setState {
                        copy(
                            dueDate = info.dueDate,
                            startDate = info.startDate,
                            endDate = info.endDate,
                            hasMultipleBirth = info.hasMultipleBirth,
                            hasMiscarriageHistory = info.hasMiscarriageHistory
                        )
                    }
                    sendEffect(MaternityEffect.ShowToast("저장된 정보를 불러왔습니다"))
                }
                .onFailure { error ->
                    println("[ViewModel] 출산휴직 정보 로드 실패: ${error.message}")
                    sendEffect(MaternityEffect.ShowToast("기본 정보를 설정했습니다"))
                }
        }
    }

    private fun updateDueDate(value: String) {
        setState { copy(dueDate = value) }
    }

    private fun updateStartDate(value: String) {
        setState { copy(startDate = value) }
    }

    private fun updateEndDate(value: String) {
        setState { copy(endDate = value) }
    }

    private fun updateMultipleBirth(value: Boolean) {
        setState { copy(hasMultipleBirth = value) }
    }

    private fun updateMiscarriage(value: Boolean) {
        setState { copy(hasMiscarriageHistory = value) }
    }

    private fun calculatePay() {
        viewModelScope.launch {
            setState { copy(isLoading = true) }
            println("[ViewModel] 급여 계산 시작")

            try {
                // 현재 상태에서 MaternityInfo 생성
                val currentState = state.value
                val maternityInfo = MaternityInfo(
                    dueDate = currentState.dueDate,
                    startDate = currentState.startDate,
                    endDate = currentState.endDate,
                    hasMultipleBirth = currentState.hasMultipleBirth ?: false,
                    hasMiscarriageHistory = currentState.hasMiscarriageHistory ?: false
                )

                // 급여 정보 가져오기
                val salaryResult = getUserSalaryInfoUseCase()
                salaryResult
                    .onSuccess { salaryInfo ->
                        // 급여 계산 실행
                        calculateMaternityPayUseCase(maternityInfo, salaryInfo)
                            .onSuccess { result ->
                                println("[ViewModel] 급여 계산 성공: $result")
                                setState {
                                    copy(
                                        isLoading = false,
                                        first30DaysPay = result.first30DaysPay.toInt().toString(),
                                        second30DaysPay = result.second30DaysPay.toInt().toString(),
                                        third30DaysPay = result.third30DaysPay.toInt().toString(),
                                        compensation = result.totalCompensation.toInt().toString(),
                                        calculationDetails = result.calculationDetails
                                    )
                                }
                                sendEffect(MaternityEffect.ShowCalculationResult(
                                    "총 급여: ${result.totalCompensation.toInt()}원"
                                ))
                            }
                            .onFailure { error ->
                                println("[ViewModel] 급여 계산 실패: ${error.message}")
                                setState { copy(isLoading = false) }
                                sendEffect(MaternityEffect.ShowError("계산 중 오류가 발생했습니다: ${error.message}"))
                            }
                    }
                    .onFailure { error ->
                        println("[ViewModel] 급여 정보 조회 실패: ${error.message}")
                        setState { copy(isLoading = false) }
                        sendEffect(MaternityEffect.ShowError("급여 정보를 불러올 수 없습니다"))
                    }
            } catch (e: Exception) {
                println("[ViewModel] 계산 중 예외 발생: ${e.message}")
                setState { copy(isLoading = false) }
                sendEffect(MaternityEffect.ShowError("계산 중 오류가 발생했습니다"))
            }
        }
    }

    private fun saveCurrentInfo() {
        viewModelScope.launch {
            val currentState = state.value
            val maternityInfo = MaternityInfo(
                dueDate = currentState.dueDate,
                startDate = currentState.startDate,
                endDate = currentState.endDate,
                hasMultipleBirth = currentState.hasMultipleBirth ?: false,
                hasMiscarriageHistory = currentState.hasMiscarriageHistory ?: false
            )

            saveMaternityInfoUseCase(maternityInfo)
                .onSuccess {
                    println("[ViewModel] 정보 저장 성공")
                    sendEffect(MaternityEffect.ShowToast("정보가 저장되었습니다"))
                }
                .onFailure { error ->
                    println("[ViewModel] 정보 저장 실패: ${error.message}")
                    sendEffect(MaternityEffect.ShowError("저장 중 오류가 발생했습니다"))
                }
        }
    }
}