package com.example.yookcalc.util

interface Logger {
    fun d(tag: String, message: String)
    fun e(tag: String, message: String, throwable: Throwable? = null)

    companion object {
        const val TAG_REPOSITORY = "Repository"
        const val TAG_VIEWMODEL = "ViewModel"
        const val TAG_UI = "UI"
        const val TAG_DATASOURCE = "DataSource"
    }
}