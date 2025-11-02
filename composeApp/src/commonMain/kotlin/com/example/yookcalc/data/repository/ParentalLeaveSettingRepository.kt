package com.example.yookcalc.data.repository

import com.example.yookcalc.data.model.ParentalLeavePolicy

interface ParentalLeaveSettingRepository {
    suspend fun getLeavePaySettings(): List<ParentalLeavePolicy>?
}