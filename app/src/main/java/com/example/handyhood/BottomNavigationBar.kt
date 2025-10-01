package com.example.handyhood

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun BottomNavigationBar(
    screens: List<Screen>,
    currentRoute: String?,
    onScreenSelected: (Screen) -> Unit
) {
    NavigationBar(
        containerColor = MaterialTheme.colorScheme.surfaceVariant
    ) {
        screens.forEach { screen ->
            NavigationBarItem(
                icon = {
                    Icon(
                        imageVector = screen.icon,
                        contentDescription = screen.title
                    )
                },
                label = { Text(screen.title) },
                selected = currentRoute == screen.route,
                onClick = { onScreenSelected(screen) },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.primary,
                    selectedTextColor = MaterialTheme.colorScheme.primary,
                    unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant
                )
            )
        }
    }
}
