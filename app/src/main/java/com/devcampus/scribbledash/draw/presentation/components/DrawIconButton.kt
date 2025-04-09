package com.devcampus.scribbledash.draw.presentation.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp

@Composable
fun DrawIconButton(
    @DrawableRes icon: Int,
    isEnabled: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = Modifier
            .size(64.dp)
            .clip(RoundedCornerShape((22.dp)))
            .background(
                MaterialTheme.colorScheme.surfaceContainerLow.copy(
                    alpha = if (isEnabled) 1f else 0.4f
                )
            )
            .clickable(isEnabled) {
                onClick()
            }
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(icon),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onBackground.copy(
                alpha = if (isEnabled) 1f else 0.4f
            ),
            modifier = modifier
                .size(28.dp)
                .align(Alignment.Center)
        )
    }
}