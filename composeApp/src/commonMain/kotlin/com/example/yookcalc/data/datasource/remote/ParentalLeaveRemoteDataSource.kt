package com.example.yookcalc.data.datasource.remote

import com.example.yookcalc.data.model.ParentalLeavePolicy

interface ParentalLeaveRemoteDataSource {
    suspend fun getLeavePay(): List<ParentalLeavePolicy>?
}