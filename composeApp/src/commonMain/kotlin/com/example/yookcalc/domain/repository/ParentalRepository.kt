package com.example.yookcalc.domain.repository

import com.example.yookcalc.domain.entity.ParentalCalculationResult
import com.example.yookcalc.domain.entity.ParentalInfo
import com.example.yookcalc.domain.entity.UserSalaryInfo

interface ParentalRepository {
    suspend fun getParentalInfo(): Result<ParentalInfo>
    suspend fun saveParentalInfo(parentalInfo: ParentalInfo)
    suspend fun calculateParentalPay(
        info: ParentalInfo,
        salaryInfo: UserSalaryInfo
    ): Result<ParentalCalculationResult>
}