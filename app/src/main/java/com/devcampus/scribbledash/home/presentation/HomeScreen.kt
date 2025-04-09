package com.devcampus.scribbledash.home.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.devcampus.core.presentation.ui.Heading
import com.devcampus.scribbledash.R
import com.devcampus.scribbledash.home.presentation.components.GameModeItem

@Composable
fun HomeScreen(
    onGameModeClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(20.dp),
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        Spacer(modifier = Modifier.height(50.dp))
        Heading(
            title = "Start Drawing!",
            subtitle = "Select game mode"
        )
        GameModeItem(
            gameModeTitle = "One Round Wonder",
            gameModeImage = R.drawable.img_one_round_wonder,
            onGameModeClicked = onGameModeClicked,
        )
    }
}