package com.example.handyhood.ui.screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.handyhood.ui.theme.HandyHoodTheme
import kotlinx.coroutines.delay

data class ProfileAction(
    val icon: androidx.compose.ui.graphics.vector.ImageVector,
    val title: String,
    val subtitle: String,
    val action: () -> Unit
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier
) {
    var isEditMode by remember { mutableStateOf(false) }
    var userName by remember { mutableStateOf("Alex Thompson") }
    var userEmail by remember { mutableStateOf("alex.thompson@email.com") }
    var userBio by remember { mutableStateOf("Community enthusiast, dog lover, and weekend DIY warrior. Always happy to help neighbors!") }
    var userLocation by remember { mutableStateOf("Oak Street Neighborhood") }

    val profileActions = listOf(
        ProfileAction(
            icon = Icons.Default.Edit,
            title = "Edit Profile",
            subtitle = "Update your information"
        ) { isEditMode = true },
        ProfileAction(
            icon = Icons.Default.LocationOn,
            title = "Location Settings",
            subtitle = "Manage your neighborhood"
        ) { /* Handle location */ },
        ProfileAction(
            icon = Icons.Default.Notifications,
            title = "Notification Preferences",
            subtitle = "Control what you receive"
        ) { /* Handle notifications */ },
        ProfileAction(
            icon = Icons.Default.Security,
            title = "Privacy & Security",
            subtitle = "Manage your privacy settings"
        ) { /* Handle privacy */ },
        ProfileAction(
            icon = Icons.Default.Help,
            title = "Help & Support",
            subtitle = "Get help using HandyHood"
        ) { /* Handle help */ },
        ProfileAction(
            icon = Icons.Default.Info,
            title = "About",
            subtitle = "App version and information"
        ) { /* Handle about */ }
    )

    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            ProfileHeader(
                userName = userName,
                userEmail = userEmail,
                userBio = userBio,
                userLocation = userLocation,
                isEditMode = isEditMode,
                onEditClick = { isEditMode = !isEditMode },
                onSaveClick = { isEditMode = false },
                onNameChange = { userName = it },
                onEmailChange = { userEmail = it },
                onBioChange = { userBio = it },
                onLocationChange = { userLocation = it }
            )
        }

        item {
            StatsCard()
        }

        items(profileActions) { action ->
            ProfileActionItem(
                action = action,
                onClick = action.action
            )
        }

        item {
            Spacer(modifier = Modifier.height(32.dp))

            OutlinedButton(
                onClick = { /* Handle logout */ },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = MaterialTheme.colorScheme.error
                )
            ) {
                Icon(
                    imageVector = Icons.Default.Logout,
                    contentDescription = "Logout"
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("Sign Out")
            }
        }
    }
}

@Composable
fun ProfileHeader(
    userName: String,
    userEmail: String,
    userBio: String,
    userLocation: String,
    isEditMode: Boolean,
    onEditClick: () -> Unit,
    onSaveClick: () -> Unit,
    onNameChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    onBioChange: (String) -> Unit,
    onLocationChange: (String) -> Unit
) {
    ElevatedCard(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Profile Picture
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primary),
                contentAlignment = Alignment.Center
            ) {
                if (isEditMode) {
                    IconButton(
                        onClick = { /* Change profile picture */ },
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Icon(
                            imageVector = Icons.Default.CameraAlt,
                            contentDescription = "Change photo",
                            tint = MaterialTheme.colorScheme.onPrimary,
                            modifier = Modifier.size(32.dp)
                        )
                    }
                } else {
                    Text(
                        text = userName.split(" ").map { it.first() }.joinToString(""),
                        style = MaterialTheme.typography.headlineLarge,
                        color = MaterialTheme.colorScheme.onPrimary,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Edit/Save Button
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                if (isEditMode) {
                    TextButton(onClick = onSaveClick) {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = "Save"
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Save")
                    }
                } else {
                    TextButton(onClick = onEditClick) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = "Edit"
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Edit")
                    }
                }
            }

            // User Information
            if (isEditMode) {
                OutlinedTextField(
                    value = userName,
                    onValueChange = onNameChange,
                    label = { Text("Name") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = userEmail,
                    onValueChange = onEmailChange,
                    label = { Text("Email") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = userLocation,
                    onValueChange = onLocationChange,
                    label = { Text("Location") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.LocationOn,
                            contentDescription = "Location"
                        )
                    }
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = userBio,
                    onValueChange = onBioChange,
                    label = { Text("Bio") },
                    modifier = Modifier.fillMaxWidth(),
                    maxLines = 3,
                    minLines = 2
                )
            } else {
                Text(
                    text = userName,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = userEmail,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.LocationOn,
                        contentDescription = "Location",
                        modifier = Modifier.size(16.dp),
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = userLocation,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = userBio,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}

@Composable
fun StatsCard() {
    ElevatedCard(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Community Stats",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                StatItem(
                    value = "23",
                    label = "Posts",
                    icon = Icons.Default.Article
                )
                StatItem(
                    value = "156",
                    label = "Likes",
                    icon = Icons.Default.Favorite
                )
                StatItem(
                    value = "4.8",
                    label = "Rating",
                    icon = Icons.Default.Star
                )
                StatItem(
                    value = "89",
                    label = "Helped",
                    icon = Icons.Default.HandHeart
                )
            }
        }
    }
}

@Composable
fun StatItem(
    value: String,
    label: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(24.dp)
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = value,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )

        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
fun ProfileActionItem(
    action: ProfileAction,
    onClick: () -> Unit
) {
    var isPressed by remember { mutableStateOf(false) }
    val cardScale by animateFloatAsState(
        targetValue = if (isPressed) 0.98f else 1f,
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy),
        label = "cardScale"
    )

    ElevatedCard(
        onClick = {
            isPressed = true
            onClick()
        },
        modifier = Modifier
            .fillMaxWidth()
            .scale(cardScale),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 1.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = action.icon,
                contentDescription = action.title,
                tint = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = action.title,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = action.subtitle,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Icon(
                imageVector = Icons.Default.ChevronRight,
                contentDescription = "Navigate",
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }

    LaunchedEffect(isPressed) {
        if (isPressed) {
            delay(100)
            isPressed = false
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview() {
    HandyHoodTheme {
        ProfileScreen()
    }
}