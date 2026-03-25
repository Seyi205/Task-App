package com.rogue21.taskapp.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.rogue21.taskapp.R

// Define your app's font family
val AppFontFamily = FontFamily(
    Font(
        resId = R.font.lexend_regular,
        weight = FontWeight.Normal
    ),
    Font(
        resId = R.font.lexend_medium,
        weight = FontWeight.Medium
    ),
    Font(
        resId = R.font.lexend_semibold,
        weight = FontWeight.SemiBold
    ),
)

// Define the typography system
val AppTypography = Typography(

    bodyLarge = TextStyle(
        fontFamily = AppFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 18.sp,
        lineHeight = 24.sp
    ),

    bodyMedium = TextStyle(
        fontFamily = AppFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 22.sp
    ),

    titleLarge = TextStyle(
        fontFamily = AppFontFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 20.sp,
        lineHeight = 26.sp
    )
)