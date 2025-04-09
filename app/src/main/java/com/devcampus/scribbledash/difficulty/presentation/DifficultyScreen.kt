package com.devcampus.scribbledash.difficulty.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.devcampus.core.presentation.ui.BaseScaffold
import com.devcampus.core.presentation.ui.Heading
import com.devcampus.scribbledash.R
import com.devcampus.scribbledash.difficulty.presentation.components.DifficultyItem

@Composable
fun DifficultyScreen(
    navigateBack: () -> Unit,
    navigateToDraw: () -> Unit,
    modifier: Modifier = Modifier
) {
    BaseScaffold(
        onClose = navigateBack
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFFEFAF6))
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(55.dp),
                modifier = Modifier
                    .fillMaxSize()
                    // .background(Color(0xFFFEFAF6))
                    .padding(top = 66.dp)
            ) {
                Heading(
                    title = "Start Drawing!",
                    subtitle = "Choose a difficulty setting"
                )
                Row(
                    horizontalArrangement = Arrangement.SpaceAround,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    DifficultyItem(
                        difficulty = "Beginner",
                        difficultyImage = R.drawable.img_pencil,
                        onDifficultySelected = navigateToDraw,
                        imageAlignment = Alignment.TopEnd
                    )

                    DifficultyItem(
                        difficulty = "Challenging",
                        difficultyImage = R.drawable.img_utensils,
                        onDifficultySelected = navigateToDraw,
                        modifier = Modifier.offset(y = (-16).dp)
                    )

                    DifficultyItem(
                        difficulty = "Master",
                        difficultyImage = R.drawable.img_palette,
                        onDifficultySelected = navigateToDraw,
                    )
                }
            }
        }
    }
}