package com.example.yookcalc.data.repository

import com.example.yookcalc.data.MaternityRemoteDataSource
import com.example.yookcalc.domain.entity.MaternityCalculationResult
import com.example.yookcalc.domain.entity.MaternityInfo
import com.example.yookcalc.domain.entity.UserSalaryInfo
import com.example.yookcalc.util.Logger
import kotlinx.coroutines.delay
import kotlin.math.min

class MaternityRemoteDataSourceImpl(
    private val logger: Logger
) : MaternityRemoteDataSource {

    override suspend fun calculateMaternityPay(
        info: MaternityInfo,
        salaryInfo: UserSalaryInfo
    ): Result<MaternityCalculationResult> {
        return try {
            delay(1000) // 네트워크 시뮬레이션

            logger.d(Logger.TAG_DATASOURCE, "출산휴직 급여 계산 시작")

            val result = calculateMaternityPayInternal(info, salaryInfo)
            logger.d(Logger.TAG_DATASOURCE, "계산 결과: $result")

            Result.success(result)
        } catch (e: Exception) {
            logger.e(Logger.TAG_DATASOURCE, "급여 계산 실패", e)
            Result.failure(e)
        }
    }

    private fun calculateMaternityPayInternal(
        info: MaternityInfo,
        salaryInfo: UserSalaryInfo
    ): MaternityCalculationResult {
        val totalDays = if (info.hasMultipleBirth) 120 else 90
        val dailyPayLimit = 200000.0
        val effectiveDailyPay = min(salaryInfo.dailyPay, dailyPayLimit)

        val first60Days = min(totalDays, 60)
        val first60DaysPay = first60Days * effectiveDailyPay

        val remaining30Days = min(totalDays - 60, 30)
        val remaining30DaysPay = remaining30Days * effectiveDailyPay * 0.8

        val extraDays = maxOf(totalDays - 90, 0)
        val extraDaysPay = extraDays * effectiveDailyPay * 0.75

        val totalCompensation = first60DaysPay + remaining30DaysPay + extraDaysPay

        val details = listOf(
            "총 휴직 기간: ${totalDays}일",
            "첫 60일 (100%): ${first60Days}일 × ${effectiveDailyPay.toInt()}원 = ${first60DaysPay.toInt()}원",
            "다음 30일 (80%): ${remaining30Days}일 × ${(effectiveDailyPay * 0.8).toInt()}원 = ${remaining30DaysPay.toInt()}원",
            if (extraDays > 0) "추가 기간 (75%): ${extraDays}일 × ${(effectiveDailyPay * 0.75).toInt()}원 = ${extraDaysPay.toInt()}원" else "",
            "총 급여: ${totalCompensation.toInt()}원"
        ).filter { it.isNotEmpty() }

        return MaternityCalculationResult(
            totalDays = totalDays,
            first30DaysPay = first60DaysPay,
            second30DaysPay = remaining30DaysPay,
            third30DaysPay = extraDaysPay,
            totalCompensation = totalCompensation,
            calculationDetails = details
        )
    }
}
