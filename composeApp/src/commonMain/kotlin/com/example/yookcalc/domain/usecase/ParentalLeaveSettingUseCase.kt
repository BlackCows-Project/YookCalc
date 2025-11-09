package com.example.yookcalc.domain.usecase

import com.example.yookcalc.data.repository.ParentalLeaveSettingRepository
import com.example.yookcalc.data.settings.ParentalLeavePaySetting
import com.example.yookcalc.domain.repository.ParentalLeaveSettingRepositoryImpl
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.launch

class ParentalLeaveSettingUseCase(private val repository: ParentalLeaveSettingRepository = ParentalLeaveSettingRepositoryImpl()) {
    operator fun invoke(): Flow<Unit> {
        return channelFlow {
            if (ParentalLeavePaySetting.isInitialized) {
                trySend(Unit)
            }

            val settings = async { repository.getLeavePaySettings() }

            val defaultSettingJob = launch {
                delay(3000L)
                ParentalLeavePaySetting.setParentalLeavePaySetting(null)
                trySend(Unit)
            }

            launch {
                ParentalLeavePaySetting.setParentalLeavePaySetting(settings.await())
                defaultSettingJob.cancel()
                trySend(Unit)
            }
        }
    }
}