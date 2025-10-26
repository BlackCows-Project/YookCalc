package com.example.yookcalc.presentation.feature.parental

import com.example.yookcalc.base.BaseViewModel
import com.example.yookcalc.domain.entity.ParentalInfo
import com.example.yookcalc.domain.usecase.Parental.CalculateParentalPayUseCase
import com.example.yookcalc.domain.usecase.Parental.GetParentalInfoUseCase
import com.example.yookcalc.domain.usecase.GetUserSalaryInfoUseCase
import com.example.yookcalc.domain.usecase.Parental.SaveParentalInfoUseCase
import kotlinx.coroutines.launch

class ParentalViewModel(
    private val getParentalInfoUseCase: GetParentalInfoUseCase,
    private val saveParentalInfoUseCase: SaveParentalInfoUseCase,
    private val calculateParentalPayUseCase: CalculateParentalPayUseCase,
    private val getUserSalaryInfoUseCase: GetUserSalaryInfoUseCase
) : BaseViewModel<ParentalUiState, ParentalEvent, ParentalEffect>(ParentalUiState()) {

    override fun onEvent(event: ParentalEvent) {
        println("[ParentalViewModel] 이벤트 발생: $event")
        when (event) {
            is ParentalEvent.LoadDefaultData -> loadDefaults()
            is ParentalEvent.ChangeBirthDate -> updateBirthDate(event.value)
            is ParentalEvent.SelectReducedWorkingHours -> updateReducedWorkingHours(event.value)
            is ParentalEvent.ChangeStartDate -> updateStartDate(event.value)
            is ParentalEvent.ChangeEndDate -> updateEndDate(event.value)
            is ParentalEvent.SelectSpouseParentalLeave -> updateSpouseParentalLeave(event.value)
            is ParentalEvent.ChangeMonthlyPay -> updateMonthlyPay(event.value)
            is ParentalEvent.CalculateParentalPay -> calculatePay()
            is ParentalEvent.SaveCurrentInfo -> saveCurrentInfo()
        }
    }

    private fun loadDefaults() {
        viewModelScope.launch {
            println("[ParentalViewModel] 기본 데이터 로드 시작")

            getParentalInfoUseCase()
                .onSuccess { info ->
                    println("[ParentalViewModel] 육아휴직 정보 로드 성공: $info")
                    setState {
                        copy(
                            birthDate = info.birthDate,
                            useReducedWorkingHours = info.useReducedWorkingHours,
                            startDate = info.startDate,
                            endDate = info.endDate,
                            useSpouseParentalLeave = info.useSpouseParentalLeave
                        )
                    }
                    sendEffect(ParentalEffect.ShowToast("저장된 정보를 불러왔습니다"))
                }
                .onFailure { error ->
                    println("[ParentalViewModel] 육아휴직 정보 로드 실패: ${error.message}")
                    sendEffect(ParentalEffect.ShowToast("기본 정보를 설정했습니다"))
                }
        }
    }

    private fun updateBirthDate(value: String) {
        setState { copy(birthDate = value) }
    }

    private fun updateReducedWorkingHours(value: Boolean) {
        setState { copy(useReducedWorkingHours = value) }
    }

    private fun updateStartDate(value: String) {
        setState { copy(startDate = value) }
    }

    private fun updateEndDate(value: String) {
        setState { copy(endDate = value) }
    }

    private fun updateSpouseParentalLeave(value: Boolean) {
        setState { copy(useSpouseParentalLeave = value) }
    }

    private fun updateMonthlyPay(value: String) {
        setState { copy(monthlyPay = value) }
    }

    private fun calculatePay() {
        viewModelScope.launch {
            setState { copy(isLoading = true) }
            println("[ParentalViewModel] 급여 계산 시작")

            try {
                val currentState = state.value
                val parentalInfo = ParentalInfo(
                    birthDate = currentState.birthDate,
                    useReducedWorkingHours = currentState.useReducedWorkingHours ?: false,
                    startDate = currentState.startDate,
                    endDate = currentState.endDate,
                    useSpouseParentalLeave = currentState.useSpouseParentalLeave ?: false
                )

                val salaryResult = getUserSalaryInfoUseCase()
                salaryResult
                    .onSuccess { salaryInfo ->
                        calculateParentalPayUseCase(parentalInfo, salaryInfo)
                            .onSuccess { result ->
                                println("[ParentalViewModel] 급여 계산 성공: $result")
                                setState {
                                    copy(
                                        isLoading = false,
                                        totalCompensation = result.totalCompensation.toInt().toString(),
                                        calculationDetails = result.calculationDetails
                                    )
                                }
                                sendEffect(ParentalEffect.ShowCalculationResult(
                                    "총 급여: ${result.totalCompensation.toInt()}원"
                                ))
                            }
                            .onFailure { error ->
                                println("[ParentalViewModel] 급여 계산 실패: ${error.message}")
                                setState { copy(isLoading = false) }
                                sendEffect(ParentalEffect.ShowError("계산 중 오류가 발생했습니다: ${error.message}"))
                            }
                    }
                    .onFailure { error ->
                        println("[ParentalViewModel] 급여 정보 조회 실패: ${error.message}")
                        setState { copy(isLoading = false) }
                        sendEffect(ParentalEffect.ShowError("급여 정보를 불러올 수 없습니다"))
                    }
            } catch (e: Exception) {
                println("[ParentalViewModel] 계산 중 예외 발생: ${e.message}")
                setState { copy(isLoading = false) }
                sendEffect(ParentalEffect.ShowError("계산 중 오류가 발생했습니다"))
            }
        }
    }

    private fun saveCurrentInfo() {
        viewModelScope.launch {
            val currentState = state.value
            val parentalInfo = ParentalInfo(
                birthDate = currentState.birthDate,
                useReducedWorkingHours = currentState.useReducedWorkingHours ?: false,
                startDate = currentState.startDate,
                endDate = currentState.endDate,
                useSpouseParentalLeave = currentState.useSpouseParentalLeave ?: false
            )

            saveParentalInfoUseCase(parentalInfo)
                .onSuccess {
                    println("[ParentalViewModel] 정보 저장 성공")
                    sendEffect(ParentalEffect.ShowToast("정보가 저장되었습니다"))
                }
                .onFailure { error ->
                    println("[ParentalViewModel] 정보 저장 실패: ${error.message}")
                    sendEffect(ParentalEffect.ShowError("저장 중 오류가 발생했습니다"))
                }
        }
    }
}