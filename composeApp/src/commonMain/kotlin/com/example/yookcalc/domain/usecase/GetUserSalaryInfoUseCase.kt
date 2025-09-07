package com.example.yookcalc.domain.usecase

import com.example.yookcalc.domain.entity.UserSalaryInfo
import com.example.yookcalc.domain.repository.MaternityRepository

class GetUserSalaryInfoUseCase(
    private val repository: MaternityRepository
) {
    suspend operator fun invoke(): Result<UserSalaryInfo> {
        return repository.getUserSalaryInfo()
    }
}