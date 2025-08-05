package com.example.yookcalc

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.CanvasBasedWindow
import com.example.yookcalc.ui.WebApp // WebApp 임포트

/**
 * 웹 애플리케이션의 메인 진입점입니다.
 * CanvasBasedWindow를 사용하여 Compose UI를 웹 캔버스에 렌더링합니다.
 */
@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    CanvasBasedWindow("YookCalc Web", canvasElementId = "composeAppRoot") {
        // 아무것도 렌더링하지 않습니다.
        // 이 블록이 비어 있으면 WebAssembly 런타임만 초기화됩니다.
    }
}