package com.example.yookcalc.util

class LoggerImpl : Logger {
    override fun d(tag: String, message: String) {
        println("[$tag] $message")
    }

    override fun e(tag: String, message: String, throwable: Throwable?) {
        println("[$tag] ERROR: $message")
        throwable?.printStackTrace()
    }
}