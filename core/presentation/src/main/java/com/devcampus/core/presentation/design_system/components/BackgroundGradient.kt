package com.devcampus.core.presentation.design_system.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import com.devcampus.core.presentation.design_system.ScribbleDashBackground
import com.devcampus.core.presentation.design_system.ScribbleDashBackgroundGradient

@Composable
fun BackgroundGradient(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Box(
        modifier = modifier.background(
            brush = Brush.verticalGradient(
                colors = listOf(
                    ScribbleDashBackground,

                    ScribbleDashBackgroundGradient,
                )
            )
        )
    ) {
        content()
    }

}