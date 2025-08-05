package com.example.yookcalc

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform