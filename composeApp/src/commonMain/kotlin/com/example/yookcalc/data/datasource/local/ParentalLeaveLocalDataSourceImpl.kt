package com.example.yookcalc.data.datasource.local

import com.example.yookcalc.data.model.ParentalLeavePolicy
import com.example.yookcalc.data.storage.PreferenceStorage
import com.example.yookcalc.data.storage.StorageProvider
import kotlinx.serialization.json.Json

class ParentalLeaveLocalDataSourceImpl(
    private val preferenceStorage: PreferenceStorage = StorageProvider.providePreferenceStorage(),
) : ParentalLeaveLocalDataSource {

    companion object {
        private const val KEY_PARENTAL_LEAVE_DATA = "parental_leave_data"
    }

    private val json = Json { ignoreUnknownKeys = true }

    override fun getLeavePay(): List<ParentalLeavePolicy>? {
        val jsonData = preferenceStorage.getString(KEY_PARENTAL_LEAVE_DATA)
        return jsonData?.let {
            runCatching {
                json.decodeFromString<List<ParentalLeavePolicy>>(it)
            }.getOrNull()
        }
    }

    override fun saveLeavePay(settings: List<ParentalLeavePolicy>) {
        runCatching {
            val jsonData = json.encodeToString(settings)
            preferenceStorage.putString(KEY_PARENTAL_LEAVE_DATA, jsonData)
        }.onFailure {
            preferenceStorage.remove(KEY_PARENTAL_LEAVE_DATA)
        }
    }
}