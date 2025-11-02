package com.example.yookcalc.domain.repository

import com.example.yookcalc.data.datasource.local.ParentalLeaveLocalDataSource
import com.example.yookcalc.data.datasource.local.ParentalLeaveLocalDataSourceImpl
import com.example.yookcalc.data.datasource.remote.ParentalLeaveRemoteDataSource
import com.example.yookcalc.data.datasource.remote.ParentalLeaveRemoteDataSourceImpl
import com.example.yookcalc.data.model.ParentalLeavePolicy
import com.example.yookcalc.data.repository.ParentalLeaveSettingRepository

class ParentalLeaveSettingRepositoryImpl(
    val local: ParentalLeaveLocalDataSource = ParentalLeaveLocalDataSourceImpl(),
    val remote: ParentalLeaveRemoteDataSource = ParentalLeaveRemoteDataSourceImpl(),
) : ParentalLeaveSettingRepository {
    override suspend fun getLeavePaySettings(): List<ParentalLeavePolicy>? {
        return local.getLeavePay() ?: run {
            remote.getLeavePay().also {
                if (!it.isNullOrEmpty()) {
                    local.saveLeavePay(it)
                }
            }
        }
    }
}