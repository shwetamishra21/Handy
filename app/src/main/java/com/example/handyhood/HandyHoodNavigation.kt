package com.example.handyhood.navigation

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.handyhood.components.BottomNavigationBar
import com.example.handyhood.ui.screens.*

sealed class Screen(val route: String, val title: String, val icon: androidx.compose.ui.graphics.vector.ImageVector) {
    object Welcome : Screen("welcome", "Welcome", Icons.Default.Home)
    object Dashboard : Screen("dashboard", "Dashboard", Icons.Default.Dashboard)
    object Search : Screen("search", "Search", Icons.Default.Search)
    object Inbox : Screen("inbox", "Inbox", Icons.Default.Inbox)
    object Profile : Screen("profile", "Profile", Icons.Default.Person)
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Composable
fun HandyHoodNavigation() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val bottomNavScreens = listOf(
        Screen.Dashboard,
        Screen.Search,
        Screen.Inbox,
        Screen.Profile
    )

    val showBottomBar = currentRoute in bottomNavScreens.map { it.route }

    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                BottomNavigationBar(
                    screens = bottomNavScreens,
                    currentRoute = currentRoute,
                    onScreenSelected = { screen ->
                        navController.navigate(screen.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }
        }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = Screen.Dashboard.route,
            modifier = Modifier.padding(paddingValues)
        ) {
            composable(
                Screen.Welcome.route,
                enterTransition = {
                    fadeIn(animationSpec = tween(300))
                },
                exitTransition = {
                    fadeOut(animationSpec = tween(300))
                }
            ) {
                WelcomeScreen(
                    onGetStartedClick = {
                        navController.navigate(Screen.Dashboard.route) {
                            popUpTo(Screen.Welcome.route) { inclusive = true }
                        }
                    }
                )
            }

            composable(
                Screen.Dashboard.route,
                enterTransition = {
                    slideInHorizontally(
                        initialOffsetX = { 300 },
                        animationSpec = tween(300)
                    ) + fadeIn(animationSpec = tween(300))
                },
                exitTransition = {
                    slideOutHorizontally(
                        targetOffsetX = { -300 },
                        animationSpec = tween(300)
                    ) + fadeOut(animationSpec = tween(300))
                }
            ) {
                DashboardScreen()
            }

            composable(
                Screen.Search.route,
                enterTransition = {
                    slideInHorizontally(
                        initialOffsetX = { 300 },
                        animationSpec = tween(300)
                    ) + fadeIn(animationSpec = tween(300))
                },
                exitTransition = {
                    slideOutHorizontally(
                        targetOffsetX = { -300 },
                        animationSpec = tween(300)
                    ) + fadeOut(animationSpec = tween(300))
                }
            ) {
                SearchScreen()
            }

            composable(
                Screen.Inbox.route,
                enterTransition = {
                    slideInHorizontally(
                        initialOffsetX = { 300 },
                        animationSpec = tween(300)
                    ) + fadeIn(animationSpec = tween(300))
                },
                exitTransition = {
                    slideOutHorizontally(
                        targetOffsetX = { -300 },
                        animationSpec = tween(300)
                    ) + fadeOut(animationSpec = tween(300))
                }
            ) {
                InboxScreen()
            }

            composable(
                Screen.Profile.route,
                enterTransition = {
                    slideInHorizontally(
                        initialOffsetX = { 300 },
                        animationSpec = tween(300)
                    ) + fadeIn(animationSpec = tween(300))
                },
                exitTransition = {
                    slideOutHorizontally(
                        targetOffsetX = { -300 },
                        animationSpec = tween(300)
                    ) + fadeOut(animationSpec = tween(300))
                }
            ) {
                ProfileScreen()
            }
        }
    }
}