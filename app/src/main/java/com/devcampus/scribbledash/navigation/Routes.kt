package com.devcampus.scribbledash.navigation

import com.devcampus.scribbledash.R
import kotlinx.serialization.Serializable

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