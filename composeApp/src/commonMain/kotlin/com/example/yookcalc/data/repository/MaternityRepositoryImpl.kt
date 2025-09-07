package com.example.yookcalc.data.repository

import com.example.yookcalc.data.MaternityLocalDataSource
import com.example.yookcalc.data.MaternityRemoteDataSource
import com.example.yookcalc.domain.entity.MaternityCalculationResult
import com.example.yookcalc.domain.entity.MaternityInfo
import com.example.yookcalc.domain.entity.UserSalaryInfo
import com.example.yookcalc.domain.repository.MaternityRepository
import com.example.yookcalc.util.Logger
import kotlinx.coroutines.flow.Flow

class MaternityRepositoryImpl(
    private val localDataSource: MaternityLocalDataSource,
    private val remoteDataSource: MaternityRemoteDataSource,
    private val logger: Logger
) : MaternityRepository {

    override suspend fun saveMaternityInfo(info: MaternityInfo): Result<Unit> {
        logger.d(Logger.TAG_REPOSITORY, "출산휴직 정보 저장 요청")
        return localDataSource.saveMaternityInfo(info)
    }

    override suspend fun getMaternityInfo(): Result<MaternityInfo> {
        logger.d(Logger.TAG_REPOSITORY, "출산휴직 정보 조회 요청")
        return localDataSource.getMaternityInfo()
    }

    override suspend fun getUserSalaryInfo(): Result<UserSalaryInfo> {
        logger.d(Logger.TAG_REPOSITORY, "급여 정보 조회 요청")
        return localDataSource.getUserSalaryInfo()
    }

    override suspend fun saveUserSalaryInfo(info: UserSalaryInfo): Result<Unit> {
        logger.d(Logger.TAG_REPOSITORY, "급여 정보 저장 요청")
        return localDataSource.saveUserSalaryInfo(info)
    }

    override suspend fun calculateMaternityPay(
        info: MaternityInfo,
        salaryInfo: UserSalaryInfo
    ): Result<MaternityCalculationResult> {
        logger.d(Logger.TAG_REPOSITORY, "출산휴직 급여 계산 요청")
        return remoteDataSource.calculateMaternityPay(info, salaryInfo)
    }

    override fun getMaternityInfoFlow(): Flow<MaternityInfo> {
        return localDataSource.getMaternityInfoFlow()
    }
}