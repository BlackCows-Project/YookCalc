package com.example.yookcalc.domain.repository

import com.example.yookcalc.domain.entity.MaternityCalculationResult
import com.example.yookcalc.domain.entity.MaternityInfo
import com.example.yookcalc.domain.entity.UserSalaryInfo
import kotlinx.coroutines.flow.Flow

interface MaternityRepository {
    suspend fun saveMaternityInfo(info: MaternityInfo): Result<Unit>
    suspend fun getMaternityInfo(): Result<MaternityInfo>
    suspend fun getUserSalaryInfo(): Result<UserSalaryInfo>
    suspend fun saveUserSalaryInfo(info: UserSalaryInfo): Result<Unit>
    suspend fun calculateMaternityPay(
        info: MaternityInfo,
        salaryInfo: UserSalaryInfo
    ): Result<MaternityCalculationResult>
    fun getMaternityInfoFlow(): Flow<MaternityInfo>
}
