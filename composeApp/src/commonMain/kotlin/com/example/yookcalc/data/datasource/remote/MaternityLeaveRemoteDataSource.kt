package com.example.yookcalc.data.datasource.remote

import com.example.yookcalc.data.model.MaternityLeavePolicy

interface MaternityLeaveRemoteDataSource {
    suspend fun getLeaveDay(): List<MaternityLeavePolicy>?
}