package com.example.yookcalc.di

import com.example.yookcalc.domain.usecase.Maternity.CalculateMaternityPayUseCase
import com.example.yookcalc.domain.usecase.Maternity.GetMaternityInfoUseCase
import com.example.yookcalc.domain.usecase.GetUserSalaryInfoUseCase
import com.example.yookcalc.domain.usecase.Maternity.SaveMaternityInfoUseCase
import org.koin.dsl.module

val domainModule = module {

    // UseCases
    factory {
        GetMaternityInfoUseCase(repository = get())
    }

    factory {
        SaveMaternityInfoUseCase(repository = get())
    }

    factory {
        CalculateMaternityPayUseCase(repository = get())
    }

    factory {
        GetUserSalaryInfoUseCase(repository = get())
    }
}