package com.example.yookcalc.data.repository

interface SampleRepository {
    suspend fun getSample(): String
}