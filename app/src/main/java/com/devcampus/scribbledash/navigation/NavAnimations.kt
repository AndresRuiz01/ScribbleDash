package com.devcampus.scribbledash.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.toRoute



class NavAnimations {

    companion object {

        @Composable
        fun TabNavHost(
            navController: NavHostController,
            modifier: Modifier = Modifier,
            builder: NavGraphBuilder.() -> Unit
        ) {
            NavHost(
                navController = navController,
                startDestination = Home,
                enterTransition = {
                    tabEnterAnimation(initialState, targetState)
                },
                exitTransition = {
                    tabExitAnimation(initialState, targetState)
                },
                modifier = modifier
            ) {
                builder()
            }
        }

        private fun tabEnterAnimation(
            initialState: NavBackStackEntry,
            targetState: NavBackStackEntry
        ): EnterTransition {
            val initialIndex = getIndexForRoute(initialState, topLevelRoutes)
            val targetIndex = getIndexForRoute(targetState, topLevelRoutes)

            if (initialIndex == -1 || targetIndex == -1) {
                println("Enter Anim: Cannot determine indices ($initialIndex, $targetIndex). Falling back to fadeIn.")
                return fadeIn(animationSpec = tween(300)) // Fallback
            }

            return if (targetIndex > initialIndex) {
                // Slide in from right
                slideInHorizontally(initialOffsetX = { fullWidth -> fullWidth }, animationSpec = tween(300)) +
                        fadeIn(animationSpec = tween(300))
            } else if (targetIndex < initialIndex) {
                // Slide in from left
                slideInHorizontally(initialOffsetX = { fullWidth -> -fullWidth }, animationSpec = tween(300)) +
                        fadeIn(animationSpec = tween(300))
            } else {
                // No slide first time or same index
                fadeIn(animationSpec = tween(300))
            }
        }

        private fun tabExitAnimation(
            initialState: NavBackStackEntry,
            targetState: NavBackStackEntry
        ): ExitTransition {
            val initialIndex = getIndexForRoute(initialState, topLevelRoutes)
            val targetIndex = getIndexForRoute(targetState, topLevelRoutes)

            if (initialIndex == -1 || targetIndex == -1) {
                println("Exit Anim: Cannot determine indices ($initialIndex, $targetIndex). Falling back to fadeOut.")
                return fadeOut(animationSpec = tween(300)) // Fallback
            }

            return if (targetIndex > initialIndex) {
                // Slide out to left
                slideOutHorizontally(targetOffsetX = { fullWidth -> -fullWidth }, animationSpec = tween(300)) +
                        fadeOut(animationSpec = tween(300))
            } else if (targetIndex < initialIndex) {
                // Slide out to right
                slideOutHorizontally(targetOffsetX = { fullWidth -> fullWidth }, animationSpec = tween(300)) +
                        fadeOut(animationSpec = tween(300))
            } else {
                // No slide first time or same index
                fadeOut(animationSpec = tween(300))
            }
        }

        private fun getIndexForRoute(entry: NavBackStackEntry, routes: List<TopLevelRoute<*>>): Int {
            // --- Primary Method: Use toRoute<Any> ---
            try {
                // Attempt to get the actual route object using the typesafe extension.
                // This is the preferred and most robust method.
                val currentRouteObject = entry.toRoute<Any>() // Expects Any or a common base class/interface
                val index = routes.indexOfFirst { it.route == currentRouteObject }
                if (index != -1) {
                    // Found using toRoute - this is the ideal case
                    // println("getIndexForRoute: Found index $index via toRoute for $currentRouteObject")
                    return index
                }
                // If toRoute returned something but it didn't match our list (shouldn't happen if routes are setup correctly)
                println("getIndexForRoute: WARN - toRoute returned '$currentRouteObject', but not found in topLevelRoutes.")
                // Fall through to fallback... might indicate a setup issue.

            } catch (e: IllegalArgumentException) {
                // This catch block is expected if toRoute fails (e.g., during transitions,
                // or if the route isn't correctly registered/serializable).
                println("getIndexForRoute: INFO - entry.toRoute<Any>() failed (${e.message}). Trying fallback.")
                // Proceed to fallback method below
            } catch (e: Exception) {
                // Catch other potential exceptions during toRoute conversion
                println("getIndexForRoute: ERROR - Unexpected error during entry.toRoute<Any>(): ${e.message}")
                // Proceed to fallback method below
            }

            // --- Fallback Method: Compare destination.route (String?) with class name ---
            val routeString = entry.destination.route // This is often the fully qualified class name string
            if (routeString != null) {
                val index = routes.indexOfFirst { topLevelRoute ->
                    // Compare the string route from the destination with the qualified name of the route object's class
                    routeString == topLevelRoute.route::class.qualifiedName
                }
                if (index != -1) {
                    // Found using fallback string comparison
                    // println("getIndexForRoute: Found index $index via fallback string comparison for '$routeString'")
                    return index
                }
            }

            // --- If Neither Method Worked ---
            println("getIndexForRoute: ERROR - Failed to find index for destination route: '${entry.destination.route}' using both methods.")
            return -1 // Indicate not found
        }
    }
}