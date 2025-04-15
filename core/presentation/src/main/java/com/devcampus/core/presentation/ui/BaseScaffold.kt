package com.devcampus.core.presentation.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.devcampus.core.presentation.R


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BaseScaffold(
    onClose: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable (PaddingValues) -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { },
                actions = {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.ic_close),
                        tint = MaterialTheme.colorScheme.onSurface,
                        contentDescription = null,
                        modifier = Modifier
                            .padding(12.dp)
                            .clickable(
                                interactionSource = null,
                                indication = null
                            ) {
                                onClose()
                            }
                    )
                },
                expandedHeight = 72.dp,
                colors = TopAppBarDefaults.topAppBarColors().copy(
                    containerColor = Color.Transparent
                ),
                modifier = Modifier
                    .statusBarsPadding()
            )
        },
        modifier = modifier
    ) { innerPadding ->
        content(innerPadding)
    }
}