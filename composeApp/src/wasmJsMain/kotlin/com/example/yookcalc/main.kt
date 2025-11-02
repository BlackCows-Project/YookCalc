package com.example.yookcalc

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.ComposeViewport
import kotlinx.browser.document

/**
 * 웹 애플리케이션의 메인 진입점입니다.
 * CanvasBasedWindow를 사용하여 Compose UI를 웹 캔버스에 렌더링합니다.
 */
@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    ComposeViewport(document.body!!) {
        AppEntry()
    }
}