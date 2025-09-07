package com.example.yookcalc.domain.usecase

import com.example.yookcalc.domain.entity.MaternityInfo
import com.example.yookcalc.domain.repository.MaternityRepository
import kotlinx.coroutines.flow.Flow

class GetMaternityInfoUseCase(
    private val repository: MaternityRepository
) {
    suspend operator fun invoke(): Result<MaternityInfo> {
        return repository.getMaternityInfo()
    }

    fun flow(): Flow<MaternityInfo> {
        return repository.getMaternityInfoFlow()
    }
}