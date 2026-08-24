package com.example.product_catalog_android.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.product_catalog_android.R

val InterFontFamily = FontFamily(
    Font(R.font.inter_regular, FontWeight.Normal),
    Font(R.font.inter_medium, FontWeight.Medium),
    Font(R.font.inter_semibold, FontWeight.SemiBold),
    Font(R.font.inter_bold, FontWeight.Bold)
)

val AppTypography = Typography(
    headlineLarge = TextStyle(
        fontFamily = InterFontFamily
    ),
    headlineMedium = TextStyle(
        fontFamily = InterFontFamily
    ),
    headlineSmall = TextStyle(
        fontFamily = InterFontFamily
    ),

    titleLarge = TextStyle(
        fontFamily = InterFontFamily
    ),
    titleMedium = TextStyle(
        fontFamily = InterFontFamily
    ),
    titleSmall = TextStyle(
        fontFamily = InterFontFamily
    ),

    bodyLarge = TextStyle(
        fontFamily = InterFontFamily
    ),
    bodyMedium = TextStyle(
        fontFamily = InterFontFamily
    ),
    bodySmall = TextStyle(
        fontFamily = InterFontFamily
    ),

    labelLarge = TextStyle(
        fontFamily = InterFontFamily
    ),
    labelMedium = TextStyle(
        fontFamily = InterFontFamily
    ),
    labelSmall = TextStyle(
        fontFamily = InterFontFamily
    )
)