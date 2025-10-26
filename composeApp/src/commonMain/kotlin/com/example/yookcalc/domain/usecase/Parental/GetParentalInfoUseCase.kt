package com.example.yookcalc.domain.usecase.Parental

import com.example.yookcalc.domain.entity.ParentalInfo
import com.example.yookcalc.domain.repository.ParentalRepository

/**
 * 저장된 육아휴직 정보를 불러오는 UseCase
 */
class GetParentalInfoUseCase(
    private val parentalRepository: ParentalRepository
) {
    suspend operator fun invoke(): Result<ParentalInfo> {
        return try {
            parentalRepository.getParentalInfo()
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}