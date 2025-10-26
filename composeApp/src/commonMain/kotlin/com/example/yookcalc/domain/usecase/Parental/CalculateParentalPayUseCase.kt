package com.example.yookcalc.domain.usecase.Parental

import com.example.yookcalc.domain.entity.ParentalCalculationResult
import com.example.yookcalc.domain.entity.ParentalInfo
import com.example.yookcalc.domain.entity.UserSalaryInfo
import com.example.yookcalc.domain.repository.ParentalRepository

/**
 * 육아휴직 급여를 계산하는 UseCase
 */
class CalculateParentalPayUseCase(
    private val repository: ParentalRepository
) {
    suspend operator fun invoke(
        info: ParentalInfo,
        salaryInfo: UserSalaryInfo
    ): Result<ParentalCalculationResult> {
        return repository.calculateParentalPay(info, salaryInfo)
    }
}