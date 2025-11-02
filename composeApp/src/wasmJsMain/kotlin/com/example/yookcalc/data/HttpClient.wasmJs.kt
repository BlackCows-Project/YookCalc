package com.example.yookcalc.data

import io.ktor.client.*
import io.ktor.client.engine.js.*   // Js 엔진은 js + wasmJs에서 사용
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*

actual fun httpClient() = HttpClient(Js) {
    install(ContentNegotiation) { json() }
}
