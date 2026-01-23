package com.example.yookcalc.data.datasource.remote

import com.example.yookcalc.data.api.ApiService
import com.example.yookcalc.data.api.ParentalLeavePolicyResponse
import com.example.yookcalc.data.model.ParentalLeavePolicy
import io.ktor.http.HttpMethod

class ParentalLeaveRemoteDataSourceImpl: ParentalLeaveRemoteDataSource {

    companion object {
        const val PARENTAL_LEAVE_PATH = "childcare-leave-pay/policy"
    }

    override suspend fun getLeavePay(): List<ParentalLeavePolicy>? {
        return ApiService.requestResult<List<ParentalLeavePolicyResponse>>(
            method = HttpMethod.Get,
            path = PARENTAL_LEAVE_PATH,
        ).getOrNull()?.mapNotNull { it.toParentalLeavePolicy() }
    }
}