package com.example.yookcalc.data.repository

import com.example.yookcalc.data.MaternityLocalDataSource
import com.example.yookcalc.domain.entity.MaternityInfo
import com.example.yookcalc.domain.entity.UserSalaryInfo
import com.example.yookcalc.util.Logger
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class MaternityLocalDataSourceImpl(
    private val logger: Logger
) : MaternityLocalDataSource {

    private var maternityInfoStorage: MaternityInfo? = null
    private var userSalaryInfoStorage: UserSalaryInfo? = null

    private val _maternityInfoFlow = MutableStateFlow(getDefaultMaternityInfo())

    private fun getDefaultMaternityInfo() = MaternityInfo(
        dueDate = "2025.06.16",
        startDate = "2025.06.16",
        endDate = "2025.09.13",
        hasMultipleBirth = false,
        hasMiscarriageHistory = false
    )

    private fun getDefaultUserSalaryInfo() = UserSalaryInfo(
        monthlyPay = 3000000.0,
        dailyPay = 100000.0,
        baseSalary = 2500000.0
    )

    override suspend fun saveMaternityInfo(info: MaternityInfo): Result<Unit> {
        return try {
            maternityInfoStorage = info
            _maternityInfoFlow.value = info
            logger.d(Logger.TAG_DATASOURCE, "출산휴직 정보 저장: $info")
            Result.success(Unit)
        } catch (e: Exception) {
            logger.e(Logger.TAG_DATASOURCE, "출산휴직 정보 저장 실패", e)
            Result.failure(e)
        }
    }

    override suspend fun getMaternityInfo(): Result<MaternityInfo> {
        return try {
            val info = maternityInfoStorage ?: getDefaultMaternityInfo()
            logger.d(Logger.TAG_DATASOURCE, "출산휴직 정보 조회: $info")
            Result.success(info)
        } catch (e: Exception) {
            logger.e(Logger.TAG_DATASOURCE, "출산휴직 정보 조회 실패", e)
            Result.failure(e)
        }
    }

    override suspend fun saveUserSalaryInfo(info: UserSalaryInfo): Result<Unit> {
        return try {
            userSalaryInfoStorage = info
            logger.d(Logger.TAG_DATASOURCE, "급여 정보 저장: $info")
            Result.success(Unit)
        } catch (e: Exception) {
            logger.e(Logger.TAG_DATASOURCE, "급여 정보 저장 실패", e)
            Result.failure(e)
        }
    }

    override suspend fun getUserSalaryInfo(): Result<UserSalaryInfo> {
        return try {
            val info = userSalaryInfoStorage ?: getDefaultUserSalaryInfo()
            logger.d(Logger.TAG_DATASOURCE, "급여 정보 조회: $info")
            Result.success(info)
        } catch (e: Exception) {
            logger.e(Logger.TAG_DATASOURCE, "급여 정보 조회 실패", e)
            Result.failure(e)
        }
    }

    override fun getMaternityInfoFlow(): Flow<MaternityInfo> {
        return _maternityInfoFlow.asStateFlow()
    }
}