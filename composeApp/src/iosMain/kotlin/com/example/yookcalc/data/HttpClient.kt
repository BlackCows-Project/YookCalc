package com.example.yookcalc.data

import io.ktor.client.*
import io.ktor.client.engine.darwin.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*

actual fun httpClient() = HttpClient(Darwin) {
    install(ContentNegotiation) {
        json()
    }
}
