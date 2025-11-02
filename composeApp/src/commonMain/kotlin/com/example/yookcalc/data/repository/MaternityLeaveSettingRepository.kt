package com.example.yookcalc.data.repository

import com.example.yookcalc.data.model.MaternityLeavePolicy

interface MaternityLeaveSettingRepository {
    suspend fun getLeaveDaySettings(): List<MaternityLeavePolicy>?
}