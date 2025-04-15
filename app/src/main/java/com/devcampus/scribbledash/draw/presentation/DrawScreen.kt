package com.devcampus.scribbledash.draw.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContent
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.devcampus.core.presentation.ui.BaseScaffold
import com.devcampus.core.presentation.ui.Heading
import com.devcampus.scribbledash.draw.presentation.components.DrawButtons
import com.devcampus.scribbledash.draw.presentation.components.DrawingCanvasWrapper
import org.koin.androidx.compose.koinViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val canvasModule = module {
    viewModel { DrawScreenViewModel() }
}


@Composable
fun DrawScreenRoot(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: DrawScreenViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    DrawScreen(
        state = state,
        onAction = viewModel::onAction,
        navigateBack = navigateBack
    )
}

@Composable
fun DrawScreen(
    state: DrawScreenState,
    onAction: (CanvasScreenAction) -> Unit,
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    BaseScaffold(
        onClose = navigateBack
    ) { innerPadding ->

        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFFEFAF6))
                .padding(innerPadding)
                .padding(horizontal = 29.dp)
                .padding(bottom = 24.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(32.dp),
            ) {
                Spacer(modifier = Modifier.height(36.dp))
                Heading(
                    title = "Time to draw!"
                )
                DrawingCanvasWrapper(
                    startPath = { onAction(CanvasScreenAction.StartPath(it)) },
                    continuePath = { onAction(CanvasScreenAction.ContinuePath(it)) },
                    completePath = { onAction(CanvasScreenAction.CompletePath) },
                    paths = buildList{ addAll(state.completedPaths); add(state.currentPath) },
                )
            }
            DrawButtons(
                undo = { onAction(CanvasScreenAction.Undo) },
                isUndoEnabled = state.isUndoEnabled,
                redo = { onAction(CanvasScreenAction.Redo) },
                isRedoEnabled = state.isRedoEnabled,
                clearCanvas = { onAction(CanvasScreenAction.ClearCanvas) },
                isClearCanvasEnabled = state.isClearCanvasEnabled
            )
        }

    }
}


