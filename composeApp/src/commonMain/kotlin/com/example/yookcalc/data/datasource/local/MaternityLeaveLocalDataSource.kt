package com.example.yookcalc.data.datasource.local

import com.example.yookcalc.data.model.MaternityLeavePolicy

interface MaternityLeaveLocalDataSource {
    fun getLeaveDay(): List<MaternityLeavePolicy>?
    fun saveLeaveDay(settings: List<MaternityLeavePolicy>)
}