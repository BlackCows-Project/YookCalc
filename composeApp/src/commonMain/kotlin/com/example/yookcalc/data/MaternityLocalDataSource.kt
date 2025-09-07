package com.example.yookcalc.data

import com.example.yookcalc.domain.entity.MaternityInfo
import com.example.yookcalc.domain.entity.UserSalaryInfo
import kotlinx.coroutines.flow.Flow

interface MaternityLocalDataSource {
    suspend fun saveMaternityInfo(info: MaternityInfo): Result<Unit>
    suspend fun getMaternityInfo(): Result<MaternityInfo>
    suspend fun saveUserSalaryInfo(info: UserSalaryInfo): Result<Unit>
    suspend fun getUserSalaryInfo(): Result<UserSalaryInfo>
    fun getMaternityInfoFlow(): Flow<MaternityInfo>
}