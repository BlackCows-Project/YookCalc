package com.example.yookcalc.data.storage

import platform.Foundation.NSUserDefaults

class IosPreferenceStorage : PreferenceStorage {
    private val userDefaults = NSUserDefaults.standardUserDefaults

    override fun getString(key: String): String? = userDefaults.stringForKey(key)

    override fun putString(
        key: String,
        value: String,
    ) {
        userDefaults.setObject(value, key)
    }

    override fun getLong(
        key: String,
        defaultValue: Long,
    ): Long {
        val value = userDefaults.objectForKey(key)
        return if (value != null) {
            (value as? Long) ?: defaultValue
        } else {
            defaultValue
        }
    }

    override fun putLong(
        key: String,
        value: Long,
    ) {
        userDefaults.setObject(value, key)
    }

    override fun remove(key: String) {
        userDefaults.removeObjectForKey(key)
    }

    override fun clear() {
        val dictionary = userDefaults.dictionaryRepresentation()
        dictionary.keys.forEach { key ->
            userDefaults.removeObjectForKey(key as String)
        }
    }
}