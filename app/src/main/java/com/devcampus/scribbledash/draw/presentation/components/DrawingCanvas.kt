package com.devcampus.scribbledash.draw.presentation.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun DrawingCanvasWrapper(
    startPath: (Offset) -> Unit,
    continuePath: (Offset) -> Unit,
    completePath: () -> Unit,
    paths: List<List<Offset>>,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f)
            .shadow(
                elevation = 24.dp,
                spotColor = Color(0x33726558),
                ambientColor = Color(0x33726558),
                shape = RoundedCornerShape(36.dp),
            )
            .clip(RoundedCornerShape(36.dp))
            .background(Color.White)
    ) {
        Box(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxSize()
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    shape = RoundedCornerShape(24.dp),
                )
        ) {
            DrawingCanvas(
                startPath = startPath,
                continuePath = continuePath,
                completePath = completePath,
                paths = paths,
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(24.dp))
            )
        }
    }
}

@Composable
fun DrawingCanvas(
    startPath: (Offset) -> Unit,
    continuePath: (Offset) -> Unit,
    completePath: () -> Unit,
    paths: List<List<Offset>>,
    modifier: Modifier = Modifier,
    strokeWidth: Dp = 4.dp,
    strokeColor: Color = Color.Black
) {
    val gridLineColor = MaterialTheme.colorScheme.onSurfaceVariant
    Box(modifier = modifier) { // Use Box for potential layering (like a clear button)
        Canvas(
            // Fill the parent Box and attach pointer input handling
            modifier = Modifier
                .fillMaxSize()
                .pointerInput(Unit) { // Use Unit key for stability across recompositions
                    detectDragGestures(
                        onDragStart = { offset -> startPath(offset) },
                        onDrag = { change, _ -> continuePath(change.position) },
                        onDragEnd = { completePath() },
                        onDragCancel = { completePath() },
                    )
                }
                .pointerInput(Unit) {
                    detectTapGestures(
                        onTap = {
                            startPath(it)
                            completePath()
                        }
                    )
                }
        ) {
            // --- Drawing Logic ---

            drawGridLines(
                canvasHeight = size.height,
                canvasWidth = size.width,
                strokeWidthPx = 1.dp.toPx(),
                color = gridLineColor
            )

            val drawStyle = Stroke(width = strokeWidth.toPx(), cap = StrokeCap.Round)
            paths.forEach { pathPoints ->
                if (pathPoints.size > 1) {
                    val path = androidx.compose.ui.graphics.Path().apply {
                        moveTo(pathPoints.first().x, pathPoints.first().y)
                        pathPoints.drop(1).forEach { point ->
                            lineTo(point.x, point.y)
                        }
                    }
                    drawPath(
                        path = path,
                        color = strokeColor,
                        style = drawStyle
                    )
                } else if (pathPoints.size == 1) {
                    // Handle paths with only one point (e.g., a tap) - draw a small circle
                    drawCircle(
                        color = strokeColor,
                        radius = drawStyle.width / 2,
                        center = pathPoints.first()
                    )
                }
            }
        }
    }
}

/**
 * Helper function to draw the grid lines within a DrawScope.
 */
private fun DrawScope.drawGridLines(
    canvasWidth: Float,
    canvasHeight: Float,
    color: Color,
    strokeWidthPx: Float
) {
    // Calculate line positions
    val verticalLineX1 = canvasWidth / 3f
    val verticalLineX2 = canvasWidth * 2f / 3f
    val horizontalLineY1 = canvasHeight / 3f
    val horizontalLineY2 = canvasHeight * 2f / 3f

    // Draw vertical lines
    drawLine(
        color = color,
        start = Offset(x = verticalLineX1, y = 0f),
        end = Offset(x = verticalLineX1, y = canvasHeight),
        strokeWidth = strokeWidthPx
    )
    drawLine(
        color = color,
        start = Offset(x = verticalLineX2, y = 0f),
        end = Offset(x = verticalLineX2, y = canvasHeight),
        strokeWidth = strokeWidthPx
    )

    // Draw horizontal lines
    drawLine(
        color = color,
        start = Offset(x = 0f, y = horizontalLineY1),
        end = Offset(x = canvasWidth, y = horizontalLineY1),
        strokeWidth = strokeWidthPx
    )
    drawLine(
        color = color,
        start = Offset(x = 0f, y = horizontalLineY2),
        end = Offset(x = canvasWidth, y = horizontalLineY2),
        strokeWidth = strokeWidthPx
    )
}