package com.devcampus.scribbledash.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.devcampus.core.presentation.design_system.components.BackgroundGradient
import com.devcampus.scribbledash.chart.presentation.ChartScreen
import com.devcampus.scribbledash.difficulty.presentation.DifficultyScreen
import com.devcampus.scribbledash.draw.presentation.DrawScreenRoot
import com.devcampus.scribbledash.home.presentation.HomeScreen
import com.devcampus.scribbledash.navigation.NavAnimations.Companion.TabNavHost

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
                slideInVertically(
                    animationSpec = tween(500)
                ) {
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
                slideOutVertically(
                    animationSpec = tween(500)
                ) {
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
                    TabNavHost(
                        navController = tabNavController,
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable<Home> {
                            HomeScreen(
                                onGameModeClicked = {
                                    navController.navigate(Difficulty) { launchSingleTop = true}
                                }
                            )
                        }

                        composable<Chart> {
                            ChartScreen()
                        }
                    }
                }
            }
        }

        composable<Difficulty> {
            DifficultyScreen(
                navigateBack = { navController.popBackStack(Difficulty, true) },
                navigateToDraw = { navController.navigate(Draw) { launchSingleTop = true } }
            )
        }

        composable<Draw> {
            DrawScreenRoot(
                navigateBack = { navController.popBackStack(Draw, true) }
            )
        }

    }
}