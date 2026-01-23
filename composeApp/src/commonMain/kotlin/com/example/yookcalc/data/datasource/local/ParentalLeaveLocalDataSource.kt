package com.example.yookcalc.data.datasource.local

import com.example.yookcalc.data.model.ParentalLeavePolicy

interface ParentalLeaveLocalDataSource {
    fun getLeavePay(): List<ParentalLeavePolicy>?
    fun saveLeavePay(settings: List<ParentalLeavePolicy>)
}