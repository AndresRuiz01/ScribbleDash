package com.devcampus.scribbledash.draw.presentation

import androidx.compose.ui.geometry.Offset

data class DrawScreenState(
    val currentPath: List<Offset> = listOf(),
    val completedPaths: List<List<Offset>> = listOf(),
    val undoPaths: List<List<Offset>> = listOf(),
) {
    val isUndoEnabled get() = completedPaths.isNotEmpty()
    val isClearCanvasEnabled get() = completedPaths.isNotEmpty()
    val isRedoEnabled get() = undoPaths.isNotEmpty()
}