package com.example.handyhood.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.handyhood.ui.theme.HandyHoodTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen() {
    val posts = listOf(
        "Lost Cat - Fluffy" to "Sarah M.",
        "Community BBQ This Saturday" to "Mike Johnson",
        "Handyman Available" to "Tom Builder",
        "Free Piano" to "Emma Wilson",
        "Power Outage Alert" to "Alex Chen"
    )

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(vertical = 16.dp)
    ) {
        // Header
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Text(
                        text = "HandyHood",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                    Text(
                        text = "Connect with your neighborhood",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.8f)
                    )
                }
            }
        }

        // Welcome Card
        item {
            ElevatedCard(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.elevatedCardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                )
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Welcome to HandyHood! 👋",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Connect with your neighbors, share resources, and build a stronger community together.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }

        // Quick Actions
        item {
            ElevatedCard(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Quick Actions",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        FilledTonalButton(
                            onClick = { },
                            modifier = Modifier.weight(1f)
                        ) {
                            Icon(Icons.Default.Search, contentDescription = null)
                            Spacer(Modifier.width(4.dp))
                            Text("Search")
                        }
                        FilledTonalButton(
                            onClick = { },
                            modifier = Modifier.weight(1f)
                        ) {
                            Icon(Icons.Default.Settings, contentDescription = null)
                            Spacer(Modifier.width(4.dp))
                            Text("Settings")
                        }
                    }
                }
            }
        }

        // Community Posts
        items(posts) { (title, author) ->
            ElevatedCard(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.elevatedCardElevation(2.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    // Author
                    Text(
                        text = author,
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Medium
                    )
                    Spacer(modifier = Modifier.height(4.dp))

                    // Title
                    Text(
                        text = title,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    // Sample content
                    Text(
                        text = "Community post content goes here. This is a sample description of what neighbors are sharing in HandyHood.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.height(12.dp))

                    // Actions
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        TextButton(onClick = { }) {
                            Icon(Icons.Default.Star, contentDescription = null)
                            Spacer(Modifier.width(4.dp))
                            Text("Like")
                        }
                        TextButton(onClick = { }) {
                            Icon(Icons.Default.Share, contentDescription = null)
                            Spacer(Modifier.width(4.dp))
                            Text("Share")
                        }
                        TextButton(onClick = { }) {
                            Icon(Icons.Default.Info, contentDescription = null)
                            Spacer(Modifier.width(4.dp))
                            Text("Details")
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DashboardScreenPreview() {
    HandyHoodTheme {
        DashboardScreen()
    }
}