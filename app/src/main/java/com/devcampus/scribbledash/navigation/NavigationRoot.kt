package com.devcampus.scribbledash.navigation

import androidx.annotation.DrawableRes
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.devcampus.core.presentation.design_system.ScribbleDashGreen
import com.devcampus.core.presentation.design_system.ScribbleDashOnBackgroundVariant
import com.devcampus.core.presentation.design_system.components.BackgroundGradient
import com.devcampus.scribbledash.R
import kotlinx.serialization.Serializable

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavigationRoot(modifier: Modifier = Modifier) {

    val navController = rememberNavController()
    val tabNavController = rememberNavController()
    val navBackStackEntry by tabNavController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    NavHost(
        navController = navController,
        startDestination = Tabs,
        enterTransition = {
            if (this.initialState.destination == navController.previousBackStackEntry?.destination) {
                slideInVertically {
                    it
                }
            } else {
                EnterTransition.None
            }
        },
        exitTransition = {
            if (this.initialState.destination == navController.previousBackStackEntry?.destination) {
                ExitTransition.None
            } else {
                slideOutVertically {
                    it
                }
            }
        },
    ) {
        composable<Tabs> {
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = {
                            Text(
                                text = "ScribbleDash",
                                style = MaterialTheme.typography.headlineMedium,
                                color = MaterialTheme.colorScheme.onBackground
                            )
                        },
                        colors = TopAppBarDefaults.topAppBarColors().copy(
                            containerColor = Color.Transparent
                        ),
                    )
                },
                bottomBar = {
                    NavigationBar {
                        topLevelRoutes.forEach { topLevelRoute ->
                            NavigationBarItem(
                                selected = currentDestination?.hierarchy?.any {
                                    it.hasRoute(
                                        topLevelRoute.route::class
                                    )
                                } == true,
                                onClick = {
                                    tabNavController.navigate(topLevelRoute.route) {
                                        popUpTo(tabNavController.graph.findStartDestination().id) {
                                            saveState = true
                                        }
                                        // Avoid multiple copies of the same destination when
                                        // reselecting the same item
                                        launchSingleTop = true
                                        // Restore state when reselecting a previously selected item
                                        restoreState = true
                                    }
                                },
                                icon = {
                                    Icon(
                                        imageVector = ImageVector.vectorResource(topLevelRoute.icon),
                                        contentDescription = topLevelRoute.name,
                                    )
                                },
                                colors = NavigationBarItemDefaults.colors().copy(
                                    selectedIconColor = MaterialTheme.colorScheme.primary,
                                    unselectedIconColor = MaterialTheme.colorScheme.surfaceContainerLowest,
                                    selectedIndicatorColor = Color.Transparent
                                )
                            )
                        }
                    }
                }
            ) { innerPadding ->
                BackgroundGradient(modifier = Modifier.fillMaxSize()) {
                    NavHost(
                        navController = tabNavController,
                        startDestination = Home,
                        enterTransition = {
                            NavAnimations.enterAnim(initialState, targetState)
                        },
                        exitTransition = {
                            NavAnimations.exitAnim(initialState, targetState)
                        },
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable<Home> {
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
                                    onGameModeClicked = {
                                        navController.navigate(Difficulty) { launchSingleTop = true}
                                    },
                                )
                            }
                        }

                        composable<Chart> {
                            Box(modifier = Modifier.fillMaxSize()) {
                                Text(
                                    text = "Mystery Chart Tab",
                                    style = MaterialTheme.typography.headlineMedium,
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .wrapContentSize()
                                )
                            }
                        }


                    }
                }
            }
        }

        composable<Difficulty> {
            BaseScaffold(
                onClose = {
                    navController.popBackStack(Difficulty, true)
                }
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
                                onDifficultySelected = {
                                    navController.navigate(Draw) { launchSingleTop = true }
                                },
                                imageAlignment = Alignment.TopEnd
                            )

                            DifficultyItem(
                                difficulty = "Challenging",
                                difficultyImage = R.drawable.img_utensils,
                                onDifficultySelected = {
                                    navController.navigate(Draw) { launchSingleTop = true }
                                },
                                modifier = Modifier.offset(y = (-16).dp)
                            )

                            DifficultyItem(
                                difficulty = "Master",
                                difficultyImage = R.drawable.img_palette,
                                onDifficultySelected = {
                                    navController.navigate(Draw) { launchSingleTop = true }
                                },
                            )
                        }
                    }
                }
            }
        }

        composable<Draw> {
            BaseScaffold(
                onClose = {
                    navController.popBackStack(Draw, true)
                }
            ) { innerPadding ->
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color(0xFFFEFAF6))
                        .padding(innerPadding)
                        .padding(horizontal = 16.dp)
                ) {
                    DrawingCanvas()
                }
            }
        }

    }
}





@Composable
fun Heading(
    title: String,
    modifier: Modifier = Modifier,
    subtitle: String = "",
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.displayMedium
        )
        if (subtitle.isNotEmpty()) {
            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Composable
fun DifficultyItem(
    difficulty: String,
    @DrawableRes difficultyImage: Int,
    onDifficultySelected: () -> Unit,
    modifier: Modifier = Modifier,
    imageAlignment: Alignment = Alignment.Center
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Box(
            modifier = Modifier
                .size(88.dp)
                .shadow(
                    elevation = 8.dp,
                    shape = CircleShape,
                    ambientColor = Color(0x7265581F),
                    spotColor = Color(0x7265581F)
                )
                .clickable {
                    onDifficultySelected()
                }
                .clip(CircleShape) // Clip to a circle
                .background(Color.White) // Optional background color
        ) {
            Image(
                imageVector = ImageVector.vectorResource(difficultyImage),
                contentDescription = null,
                modifier = Modifier.align(imageAlignment)
            )
        }
        Text(
            text = difficulty,
            style = MaterialTheme.typography.labelMedium,
            color = ScribbleDashOnBackgroundVariant
        )
    }
}

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


@Composable
fun DrawingCanvas(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = Modifier
            .padding(horizontal = 13.dp)
            .fillMaxWidth()
            .aspectRatio(1f)
            .shadow(
                elevation = 16.dp, // Adjust elevation to approximate desired size/blur
                 spotColor = Color(0x33726558),
                 ambientColor = Color(0x33726558),
                 shape = RoundedCornerShape(36.dp),
                // clip = false
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
            TouchDrawingCanvas(
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}



@Composable
fun TouchDrawingCanvas(
    modifier: Modifier = Modifier,
    strokeWidth: Dp = 4.dp,
    strokeColor: Color = Color.Black
) {
    // Stores the list of completed paths. Each path is a list of points (Offsets).
    // Using mutableStateListOf ensures Compose reacts to additions.
    val completedPaths = remember { mutableStateListOf<List<Offset>>() }

    // Stores the points of the path currently being drawn (while the finger is down).
    val currentPathPoints = remember { mutableStateListOf<Offset>() }

    Box(modifier = modifier) { // Use Box for potential layering (like a clear button)
        Canvas(
            // Fill the parent Box and attach pointer input handling
            modifier = Modifier
                .fillMaxSize()
                .pointerInput(Unit) { // Use Unit key for stability across recompositions
                    detectDragGestures(
                        onDragStart = { offset ->
                            // Start a new path segment when dragging begins
                            currentPathPoints.clear() // Clear previous temporary points
                            currentPathPoints.add(offset) // Add the starting point
                        },
                        onDrag = { change, _ ->
                            // Add the current pointer position to the ongoing path
                            currentPathPoints.add(change.position)
                            // Optional: Consume the event if needed to prevent parent scrolling
                            // change.consume()
                        },
                        onDragEnd = {
                            // When the drag finishes (finger lifted)
                            if (currentPathPoints.isNotEmpty()) {
                                // Add a *copy* of the current path points to the list of completed paths
                                completedPaths.add(currentPathPoints.toList())
                            }
                            // The current path points will be cleared on the next onDragStart
                        },
                        onDragCancel = {
                            // If the drag is cancelled (e.g., gesture system interruption)
                            // Treat it like onDragEnd for the path drawn so far
                            if (currentPathPoints.isNotEmpty()) {
                                completedPaths.add(currentPathPoints.toList())
                            }
                            currentPathPoints.clear() // Clear the incomplete path points
                        }
                    )
                }
        ) {
            // --- Drawing Logic ---

            val drawStyle = Stroke(width = strokeWidth.toPx(), cap = StrokeCap.Round)

            // Draw all the completed paths
            completedPaths.forEach { pathPoints ->
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

            // Draw the path currently being drawn (live preview)
            if (currentPathPoints.size > 1) {
                val currentDrawingPath = androidx.compose.ui.graphics.Path().apply {
                    moveTo(currentPathPoints.first().x, currentPathPoints.first().y)
                    currentPathPoints.drop(1).forEach { point ->
                        lineTo(point.x, point.y)
                    }
                }
                drawPath(
                    path = currentDrawingPath,
                    color = strokeColor, // Could use a different color for live preview
                    style = drawStyle
                )
            } else if (currentPathPoints.size == 1) {
                // Draw the first point of the current drag as a dot
                drawCircle(
                    color = strokeColor,
                    radius = drawStyle.width / 2,
                    center = currentPathPoints.first()
                )
            }
        }

        // Optional: Add a button to clear the canvas
        Button(
            onClick = {
                completedPaths.clear()
                currentPathPoints.clear()
            },
            modifier = Modifier
                .align(Alignment.TopEnd) // Position button
                .padding(8.dp)
        ) {
            Text("Clear")
        }
    }
}

