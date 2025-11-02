package com.example.yookcalc.data

import com.example.yookcalc.domain.entity.MaternityCalculationResult
import com.example.yookcalc.domain.entity.MaternityInfo
import com.example.yookcalc.domain.entity.UserSalaryInfo

interface MaternityRemoteDataSource {
    suspend fun calculateMaternityPay(
        info: MaternityInfo,
        salaryInfo: UserSalaryInfo
    ): Result<MaternityCalculationResult>
}