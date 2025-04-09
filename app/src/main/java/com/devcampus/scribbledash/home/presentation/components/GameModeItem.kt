package com.devcampus.scribbledash.home.presentation.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.devcampus.core.presentation.design_system.ScribbleDashGreen

@Composable
fun GameModeItem(
    gameModeTitle: String,
    @DrawableRes gameModeImage: Int,
    onGameModeClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(128.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(ScribbleDashGreen)
    ) {
        Row(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxSize()
                .clickable {
                    onGameModeClicked()
                }
                .clip(RoundedCornerShape(16.dp))
                .background(MaterialTheme.colorScheme.surfaceContainerHigh)
        ) {
            Text(
                text = gameModeTitle,
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier
                    .padding(start = 22.dp)
                    .fillMaxHeight()
                    .weight(1f)
                    .wrapContentSize()
            )

            Image(
                painter = painterResource(gameModeImage),
                contentDescription = null
            )

        }
    }
}