package com.example.yookcalc.presentation.theme

import androidx.compose.runtime.Composable

import androidx.compose.material3.Typography
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import org.jetbrains.compose.resources.Font
import yookcalc.composeapp.generated.resources.Res
import yookcalc.composeapp.generated.resources.notosans_kr

@Composable
fun getTypography(): Typography {
    val notoSansKr = FontFamily(
        Font(Res.font.notosans_kr, FontWeight.Normal),
        Font(Res.font.notosans_kr, FontWeight.Bold)
    )

    return Typography().copy(
        displayLarge = Typography().displayLarge.copy(fontFamily = notoSansKr),
        displayMedium = Typography().displayMedium.copy(fontFamily = notoSansKr),
        displaySmall = Typography().displaySmall.copy(fontFamily = notoSansKr),
        headlineLarge = Typography().headlineLarge.copy(fontFamily = notoSansKr),
        headlineMedium = Typography().headlineMedium.copy(fontFamily = notoSansKr),
        headlineSmall = Typography().headlineSmall.copy(fontFamily = notoSansKr),
        titleLarge = Typography().titleLarge.copy(fontFamily = notoSansKr),
        titleMedium = Typography().titleMedium.copy(fontFamily = notoSansKr),
        titleSmall = Typography().titleSmall.copy(fontFamily = notoSansKr),
        bodyLarge = Typography().bodyLarge.copy(fontFamily = notoSansKr),
        bodyMedium = Typography().bodyMedium.copy(fontFamily = notoSansKr),
        bodySmall = Typography().bodySmall.copy(fontFamily = notoSansKr),
        labelLarge = Typography().labelLarge.copy(fontFamily = notoSansKr),
        labelMedium = Typography().labelMedium.copy(fontFamily = notoSansKr),
        labelSmall = Typography().labelSmall.copy(fontFamily = notoSansKr)
    )
}
