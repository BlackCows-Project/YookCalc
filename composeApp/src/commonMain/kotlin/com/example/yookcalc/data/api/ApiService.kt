package com.example.yookcalc.data.api

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.parameter
import io.ktor.client.request.request
import io.ktor.client.request.url
import io.ktor.http.HttpMethod
import io.ktor.http.cio.Response
import io.ktor.http.parameters


object ApiService {

    const val BASE_URL = "https://yook-calc-be-chi.vercel.app/api/v1/"

    val httpClient by lazy { HttpClient() }

    suspend inline fun <reified T> requestResult(
        method: HttpMethod,
        path: String,
        params: Map<String, Any?> = emptyMap(),
        block: HttpRequestBuilder.() -> Unit = {}
    ): Result<T> = with(httpClient) {
        runCatching {
            val resp = request {
                url(BASE_URL + path).apply {
                    params.forEach { (key, value) ->
                        parameter(key, value)
                    }
                }
                this.method = method
                block()
            }

            resp.body()
        }
    }
}