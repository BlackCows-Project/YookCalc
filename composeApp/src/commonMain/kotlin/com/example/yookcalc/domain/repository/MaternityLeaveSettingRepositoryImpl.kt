package com.example.yookcalc.domain.repository

import com.example.yookcalc.data.datasource.local.MaternityLeaveLocalDataSource
import com.example.yookcalc.data.datasource.local.MaternityLeaveLocalDataSourceImpl
import com.example.yookcalc.data.datasource.remote.MaternityLeaveRemoteDataSource
import com.example.yookcalc.data.datasource.remote.MaternityLeaveRemoteDataSourceImpl
import com.example.yookcalc.data.model.MaternityLeavePolicy
import com.example.yookcalc.data.repository.MaternityLeaveSettingRepository

class MaternityLeaveSettingRepositoryImpl(
    val local: MaternityLeaveLocalDataSource = MaternityLeaveLocalDataSourceImpl(),
    val remote: MaternityLeaveRemoteDataSource = MaternityLeaveRemoteDataSourceImpl(),
) : MaternityLeaveSettingRepository {
    override suspend fun getLeaveDaySettings(): List<MaternityLeavePolicy>? {
        return local.getLeaveDay() ?: run {
            remote.getLeaveDay().also {
                if (!it.isNullOrEmpty()) {
                    local.saveLeaveDay(it)
                }
            }
        }
    }
}