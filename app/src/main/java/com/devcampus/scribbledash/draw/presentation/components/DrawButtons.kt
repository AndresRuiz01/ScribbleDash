package com.devcampus.scribbledash.draw.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import com.devcampus.scribbledash.R

@Composable
fun DrawButtons(
    undo: () -> Unit,
    isUndoEnabled: Boolean,
    redo: () -> Unit,
    isRedoEnabled: Boolean,
    clearCanvas: () -> Unit,
    isClearCanvasEnabled: Boolean,
    modifier: Modifier = Modifier
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        modifier = modifier
    ) {
        DrawIconButton(
            icon = R.drawable.ic_undo,
            isEnabled = isUndoEnabled,
            onClick = undo,
        )
        DrawIconButton(
            icon = R.drawable.ic_undo,
            isEnabled = isRedoEnabled,
            onClick = redo,
            modifier = Modifier.graphicsLayer {
                scaleX = -1f
            }
        )
        Box(
            modifier = Modifier
                .height(64.dp)
                .weight(1f)
                .clip(RoundedCornerShape(20.dp))
                .background(MaterialTheme.colorScheme.surfaceContainerHigh)
                .padding(6.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(16.dp))
                    .background(
                        if (isClearCanvasEnabled)
                            com.devcampus.core.presentation.design_system.ScribbleDashGreen
                        else MaterialTheme.colorScheme.surfaceContainerLowest
                    )
                    .clickable(isClearCanvasEnabled) {
                        clearCanvas()
                    }
            ) {
                Text(
                    text = "CLEAR CANVAS",
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.onPrimary.copy(
                        alpha = if (isClearCanvasEnabled) 1f else 0.8f
                    ),
                    modifier = Modifier.fillMaxSize().wrapContentSize()
                )
            }
        }
    }
}