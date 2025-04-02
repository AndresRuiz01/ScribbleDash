package com.devcampus.scribbledash.navigation

import androidx.annotation.DrawableRes
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.IntOffset
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
    val isTab = topLevelRoutes.any { topLevelRoute ->
        currentDestination?.hierarchy?.any { it.hasRoute(topLevelRoute.route::class) } == true
    }

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
        // modifier = Modifier.padding(innerPadding)
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
                            Text("Chart")
                        }


                    }
                }
            }
        }

        composable<Difficulty> {

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFFFEFAF6))
                    .systemBarsPadding()
                    .padding(horizontal = 16.dp)
            ) {
                Row(
                    horizontalArrangement = Arrangement.SpaceAround,
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.Center)
                ) {
                    DifficultyItem(
                        difficulty = "Beginner",
                        difficultyImage = R.drawable.img_pencil,
                        onDifficultySelected = {
                            navController.navigate(Draw) { launchSingleTop = true}
                        },
                        imageAlignment = Alignment.TopEnd
                    )

                    DifficultyItem(
                        difficulty = "Challenging",
                        difficultyImage = R.drawable.img_utensils,
                        onDifficultySelected = {
                            navController.navigate(Draw) { launchSingleTop = true}
                        },
                        modifier = Modifier.offset(y = (-16).dp)
                    )

                    DifficultyItem(
                        difficulty = "Master",
                        difficultyImage = R.drawable.img_palette,
                        onDifficultySelected = {
                            navController.navigate(Draw) { launchSingleTop = true}
                        },
                    )
                }
            }
        }

        composable<Draw> {
            Box(
                modifier = Modifier.fillMaxSize().background(Color.Blue)
            )
        }

    }
}

data class TopLevelRoute<T : Any>(val name: String, val route: T, val icon: Int)
val topLevelRoutes = listOf(
    TopLevelRoute("Chart", Chart, R.drawable.ic_chart),
    TopLevelRoute("Home", Home, R.drawable.ic_home),
)


@Serializable
data object Tabs

@Serializable
data object Home

@Serializable
data object Chart

@Serializable
data object Difficulty

@Serializable
data object Draw

@Composable
fun Heading(
    title: String,
    subtitle: String,
    modifier: Modifier = Modifier
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
        Text(
            text = subtitle,
            style = MaterialTheme.typography.bodyMedium
        )
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


enum class DifficultyLevel() {
    BEGINNER,
    CHALLENGING,
    MASTER
}