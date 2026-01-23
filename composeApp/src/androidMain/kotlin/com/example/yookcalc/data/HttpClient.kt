package com.example.yookcalc.data

import io.ktor.client.*
import io.ktor.client.engine.okhttp.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*

actual fun httpClient() = HttpClient(OkHttp) {
    install(ContentNegotiation) {
        json()
    }
}
