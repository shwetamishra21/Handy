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