package com.devcampus.scribbledash.draw.presentation

import androidx.compose.ui.geometry.Offset

sealed interface CanvasScreenAction {
    data class StartPath(val offset: Offset): CanvasScreenAction
    data class ContinuePath(val offset: Offset): CanvasScreenAction
    data object CompletePath: CanvasScreenAction
    data object Undo: CanvasScreenAction
    data object Redo: CanvasScreenAction
    data object ClearCanvas: CanvasScreenAction
}