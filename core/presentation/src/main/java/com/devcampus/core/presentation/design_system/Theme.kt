package com.devcampus.core.presentation.design_system

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val colorScheme = lightColorScheme(
    primary = ScribbleDashBlue,
    secondary = ScribbleDashPurple,
    tertiary = ScribbleDashOrange,
    error = ScribbleDashRed,
    background = ScribbleDashBackground,
    // backgroundGradient
    onBackground = ScribbleDashOnBackground,
    // onBackgroundVariant = Color.White,
    surfaceContainerHigh = ScribbleDashSurfaceHigh,
    surfaceContainer = ScribbleDashSurface,
    surfaceContainerLow = ScribbleDashSurfaceLow,
    surfaceContainerLowest = ScribbleDashSurfaceLowest,
    onSurface = ScribbleDashOnSurface,
    onSurfaceVariant = ScribbleDashOnSurfaceVariant
)


@Composable
fun ScribbleDashTheme(
    content: @Composable () -> Unit
) {
    val colorScheme = colorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}