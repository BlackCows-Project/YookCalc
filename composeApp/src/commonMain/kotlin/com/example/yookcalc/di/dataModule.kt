package com.example.yookcalc.di

import com.example.yookcalc.data.MaternityLocalDataSource
import com.example.yookcalc.data.MaternityRemoteDataSource
import com.example.yookcalc.data.repository.MaternityLocalDataSourceImpl
import com.example.yookcalc.data.repository.MaternityRemoteDataSourceImpl
import com.example.yookcalc.data.repository.MaternityRepositoryImpl
import com.example.yookcalc.domain.repository.MaternityRepository
import com.example.yookcalc.util.Logger
import com.example.yookcalc.util.LoggerImpl
import org.koin.dsl.module

val dataModule = module {

    // Logger
    single<Logger> { LoggerImpl() }

    // DataSources
    single<MaternityLocalDataSource> {
        MaternityLocalDataSourceImpl(logger = get())
    }

    single<MaternityRemoteDataSource> {
        MaternityRemoteDataSourceImpl(logger = get())
    }

    // Repository
    single<MaternityRepository> {
        MaternityRepositoryImpl(
            localDataSource = get(),
            remoteDataSource = get(),
            logger = get()
        )
    }
}
