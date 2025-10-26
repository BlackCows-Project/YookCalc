package com.example.yookcalc.domain.usecase.Parental


import com.example.yookcalc.domain.entity.ParentalInfo
import com.example.yookcalc.domain.repository.ParentalRepository

class SaveParentalInfoUseCase(
    private val parentalRepository: ParentalRepository
) {
    suspend operator fun invoke(parentalInfo: ParentalInfo): Result<Unit> {
        return try {
            parentalRepository.saveParentalInfo(parentalInfo)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}