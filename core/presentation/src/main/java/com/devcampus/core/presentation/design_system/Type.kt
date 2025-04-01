package com.devcampus.core.presentation.design_system

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.devcampus.scribbledash.R

val BagelFatOne = FontFamily(
    Font(
        resId = R.font.bagelfatone_regular,
        weight = FontWeight.Normal
    ),
)

val Outfit = FontFamily(
    Font(
        resId = R.font.outfit_regular,
        weight = FontWeight.Normal
    ),
    Font(
        resId = R.font.outfit_medium,
        weight = FontWeight.Medium
    ),
    Font(
        resId = R.font.outfit_semibold,
        weight = FontWeight.SemiBold
    )
)

// Set of Material typography styles to start with
val Typography.headlineXSmall: TextStyle
@Composable
get() {
    return TextStyle(
        fontFamily = BagelFatOne,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 18.sp
    )
}

val Typography.labelXLarge: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontFamily = Outfit,
            fontWeight = FontWeight.SemiBold,
            fontSize = 24.sp,
            lineHeight = 28.sp
        )
    }

val Typography = Typography(
    displayLarge = TextStyle(
        fontFamily = BagelFatOne,
        fontWeight = FontWeight.Normal,
        fontSize = 66.sp,
        lineHeight = 80.sp
    ),
    displayMedium = TextStyle(
        fontFamily = BagelFatOne,
        fontWeight = FontWeight.Normal,
        fontSize = 40.sp,
        lineHeight = 44.sp
    ),


    headlineLarge = TextStyle(
        fontFamily = BagelFatOne,
        fontWeight = FontWeight.Normal,
        fontSize = 34.sp,
        lineHeight = 48.sp
    ),
    headlineMedium = TextStyle(
        fontFamily = BagelFatOne,
        fontWeight = FontWeight.Normal,
        fontSize = 26.sp,
        lineHeight = 30.sp
    ),
    headlineSmall = TextStyle(
        fontFamily = BagelFatOne,
        fontWeight = FontWeight.Normal,
        fontSize = 18.sp,
        lineHeight = 28.sp
    ),

    bodyLarge = TextStyle(
        fontFamily = Outfit,
        fontWeight = FontWeight.Medium,
        fontSize = 20.sp,
        lineHeight = 24.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = Outfit,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp
    ),
    bodySmall = TextStyle(
        fontFamily = Outfit,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 28.sp
    ),

    labelLarge = TextStyle(
        fontFamily = Outfit,
        fontWeight = FontWeight.SemiBold,
        fontSize = 20.sp,
        lineHeight = 24.sp
    ),
    labelMedium = TextStyle(
        fontFamily = Outfit,
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp,
        lineHeight = 24.sp
    ),
    labelSmall = TextStyle(
        fontFamily = Outfit,
        fontWeight = FontWeight.SemiBold,
        fontSize = 14.sp,
        lineHeight = 18.sp
    ),
)