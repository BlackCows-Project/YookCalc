package com.example.yookcalc.domain.usecase

import com.example.yookcalc.data.repository.MaternityLeaveSettingRepository
import com.example.yookcalc.data.settings.MaternityLeaveSetting
import com.example.yookcalc.domain.repository.MaternityLeaveSettingRepositoryImpl
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.launch

class MaternityLeaveSettingUseCase(private val repository: MaternityLeaveSettingRepository = MaternityLeaveSettingRepositoryImpl()) {
    operator fun invoke(): Flow<Unit> {
        return channelFlow {
            if (MaternityLeaveSetting.isInitialized) {
                trySend(Unit)
            }

            val settings = async { repository.getLeaveDaySettings() }

            val defaultSettingJob = launch {
                delay(3000L)
                MaternityLeaveSetting.setDaysOfLeave(null)
                trySend(Unit)
            }

            launch {
                MaternityLeaveSetting.setDaysOfLeave(settings.await())
                defaultSettingJob.cancel()
                trySend(Unit)
            }
        }
    }
}