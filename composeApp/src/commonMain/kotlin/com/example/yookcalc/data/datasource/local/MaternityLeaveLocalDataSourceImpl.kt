package com.example.yookcalc.data.datasource.local

import com.example.yookcalc.data.model.MaternityLeavePolicy
import com.example.yookcalc.data.storage.PreferenceStorage
import com.example.yookcalc.data.storage.StorageProvider
import kotlinx.serialization.json.Json

class MaternityLeaveLocalDataSourceImpl(
    private val preferenceStorage: PreferenceStorage = StorageProvider.providePreferenceStorage(),
) : MaternityLeaveLocalDataSource {

    companion object {
        private const val KEY_MATERNITY_LEAVE_DATA = "maternity_leave_data"
    }

    private val json = Json { ignoreUnknownKeys = true }

    override fun getLeaveDay(): List<MaternityLeavePolicy>? {
        val jsonData = preferenceStorage.getString(KEY_MATERNITY_LEAVE_DATA)
        return jsonData?.let {
            runCatching {
                json.decodeFromString<List<MaternityLeavePolicy>>(it)
            }.getOrNull()
        }
    }

    override fun saveLeaveDay(settings: List<MaternityLeavePolicy>) {
        runCatching {
            val jsonData = json.encodeToString(settings)
            preferenceStorage.putString(KEY_MATERNITY_LEAVE_DATA, jsonData)
        }.onFailure {
            preferenceStorage.remove(KEY_MATERNITY_LEAVE_DATA)
        }
    }
}