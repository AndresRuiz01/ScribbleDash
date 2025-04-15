package com.devcampus.scribbledash.draw.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

class DrawScreenViewModel: ViewModel() {

    private val _state = MutableStateFlow(DrawScreenState())
    val state = _state
        .asStateFlow()
        .onStart {
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = DrawScreenState()
        )

    fun onAction(action: CanvasScreenAction) {
        when(action) {
            CanvasScreenAction.ClearCanvas -> {
                _state.update {
                    it.copy(
                        currentPath = listOf(),
                        completedPaths = listOf()
                    )
                }
            }
            CanvasScreenAction.CompletePath -> {
                _state.update {
                    it.copy(
                        currentPath = listOf(),
                        completedPaths = buildList { addAll(it.completedPaths); add(it.currentPath) }
                    )
                }
            }
            is CanvasScreenAction.ContinuePath -> {
                _state.update {
                    it.copy(
                        currentPath = it.currentPath + action.offset
                    )
                }
            }
            CanvasScreenAction.Redo -> {
                _state.update {
                    it.copy(
                        completedPaths = it.completedPaths + it.undoPaths.takeLast(1),
                        undoPaths = it.undoPaths.dropLast(1)
                    )
                }
            }
            is CanvasScreenAction.StartPath -> {
                _state.update {
                    it.copy(
                        currentPath = listOf(action.offset),
                        undoPaths = listOf()
                    )
                }
            }
            CanvasScreenAction.Undo -> {
                _state.update {
                    it.copy(
                        completedPaths = it.completedPaths.dropLast(1),
                        undoPaths = (it.undoPaths + it.completedPaths.takeLast(1)).takeLast(5)
                    )
                }
            }
        }
    }
}