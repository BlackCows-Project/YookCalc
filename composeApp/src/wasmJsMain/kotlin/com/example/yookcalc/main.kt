package com.example.yookcalc

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.ComposeViewport
import kotlinx.browser.document

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    println("=== main 실행됨 ===")
    ComposeViewport(document.body!!) {
        WebApp() // 기존에 만들었던 WebApp() 그대로 사용.
    }
}