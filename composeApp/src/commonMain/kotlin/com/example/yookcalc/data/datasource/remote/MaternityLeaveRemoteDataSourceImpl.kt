package com.example.yookcalc.data.datasource.remote

import com.example.yookcalc.data.api.ApiService
import com.example.yookcalc.data.api.MaternityLeavePolicyResponse
import com.example.yookcalc.data.model.MaternityLeavePolicy
import io.ktor.http.HttpMethod

class MaternityLeaveRemoteDataSourceImpl: MaternityLeaveRemoteDataSource {

    companion object {
        const val MATERNITY_LEAVE_PATH = "birth-leave/policies"
    }

    override suspend fun getLeaveDay(): List<MaternityLeavePolicy>? {
        return ApiService.requestResult<List<MaternityLeavePolicyResponse>>(
            method = HttpMethod.Get,
            path = MATERNITY_LEAVE_PATH,
        ).getOrNull()?.mapNotNull { it.toMaternityLeavePolicy() }
    }
}