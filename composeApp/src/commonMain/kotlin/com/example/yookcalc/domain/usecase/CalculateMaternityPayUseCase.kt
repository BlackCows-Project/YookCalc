package com.example.yookcalc.domain.usecase

import com.example.yookcalc.domain.entity.MaternityCalculationResult
import com.example.yookcalc.domain.entity.MaternityInfo
import com.example.yookcalc.domain.entity.UserSalaryInfo
import com.example.yookcalc.domain.repository.MaternityRepository

class CalculateMaternityPayUseCase(
    private val repository: MaternityRepository
) {
    suspend operator fun invoke(
        info: MaternityInfo,
        salaryInfo: UserSalaryInfo
    ): Result<MaternityCalculationResult> {
        return repository.calculateMaternityPay(info, salaryInfo)
    }
}