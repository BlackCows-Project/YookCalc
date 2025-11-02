package com.example.yookcalc.domain.usecase

import com.example.yookcalc.domain.entity.MaternityInfo
import com.example.yookcalc.domain.repository.MaternityRepository

class SaveMaternityInfoUseCase(
    private val repository: MaternityRepository
) {
    suspend operator fun invoke(info: MaternityInfo): Result<Unit> {
        return repository.saveMaternityInfo(info)
    }
}