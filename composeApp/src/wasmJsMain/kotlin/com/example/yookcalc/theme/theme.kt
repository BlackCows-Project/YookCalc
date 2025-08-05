package com.example.yookcalc.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable

/**
 * 앱의 Material Design 테마를 정의하는 Composable 함수입니다.
 * 시스템 테마 설정에 따라 Light 또는 Dark 모드를 적용합니다.
 */
@Composable
fun AppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(), // 시스템 다크 모드 설정 사용
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        DarkColorScheme
    } else {
        LightColorScheme
    }

    MaterialTheme(
        colorScheme = colors,
        // Typography는 별도로 정의될 수 있습니다. (예: Type.kt)
        // typography = Typography,
        content = content
    )
}