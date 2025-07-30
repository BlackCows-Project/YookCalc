package com.example.yookcalc.data.repository

class SampleRepositoryImpl : SampleRepository {
    override suspend fun getSample(): String = "KMP Sample Data"
}