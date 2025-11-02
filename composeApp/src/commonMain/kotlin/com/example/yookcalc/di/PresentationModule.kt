package com.example.yookcalc.di

import com.example.yookcalc.presentation.feature.maternity.MaternityViewModel
import org.koin.dsl.module

val presentationModule = module {

    // ViewModels
    factory {
        MaternityViewModel(
            getMaternityInfoUseCase = get(),
            saveMaternityInfoUseCase = get(),
            calculateMaternityPayUseCase = get(),
            getUserSalaryInfoUseCase = get()
        )
    }
}