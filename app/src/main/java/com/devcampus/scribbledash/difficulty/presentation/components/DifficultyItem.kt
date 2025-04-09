package com.devcampus.scribbledash.difficulty.presentation.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.devcampus.core.presentation.design_system.ScribbleDashOnBackgroundVariant

@Composable
fun DifficultyItem(
    difficulty: String,
    @DrawableRes difficultyImage: Int,
    onDifficultySelected: () -> Unit,
    modifier: Modifier = Modifier,
    imageAlignment: Alignment = Alignment.Center
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Box(
            modifier = Modifier
                .size(88.dp)
                .shadow(
                    elevation = 8.dp,
                    shape = CircleShape,
                    ambientColor = Color(0x7265581F),
                    spotColor = Color(0x7265581F)
                )
                .clickable {
                    onDifficultySelected()
                }
                .clip(CircleShape) // Clip to a circle
                .background(Color.White) // Optional background color
        ) {
            Image(
                imageVector = ImageVector.vectorResource(difficultyImage),
                contentDescription = null,
                modifier = Modifier.align(imageAlignment)
            )
        }
        Text(
            text = difficulty,
            style = MaterialTheme.typography.labelMedium,
            color = ScribbleDashOnBackgroundVariant
        )
    }
}