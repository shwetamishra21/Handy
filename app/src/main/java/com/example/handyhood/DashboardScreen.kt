package com.example.handyhood

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.handyhood.ui.theme.HandyHoodTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(modifier: Modifier = Modifier) {
    val categories = listOf("Home Services", "Pet Care", "Gardening", "Repairs", "Moving", "Tutoring")
    val recentServices = listOf(
        "Plumbing Service" to Icons.Default.Build,
        "Dog Walking" to Icons.Default.Pets,
        "House Cleaning" to Icons.Default.Home,
        "Garden Maintenance" to Icons.Default.Grass
    )

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            // Welcome Header
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "Welcome to HandyHood!",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Find trusted neighbors for all your service needs",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }

        item {
            // Categories Section
            Text(
                text = "Service Categories",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.SemiBold
            )

            Spacer(modifier = Modifier.height(8.dp))

            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(categories) { category ->
                    ElevatedFilterChip(
                        onClick = { /* Handle category selection */ },
                        label = { Text(category) },
                        selected = false
                    )
                }
            }
        }

        item {
            // Recent Services Section
            Text(
                text = "Popular Services",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.SemiBold
            )
        }

        items(recentServices) { (serviceName, icon) ->
            ElevatedCard(
                onClick = { /* Handle service selection */ },
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = serviceName,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(32.dp)
                    )

                    Spacer(modifier = Modifier.width(16.dp))

                    Column {
                        Text(
                            text = serviceName,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Medium
                        )
                        Text(
                            text = "Available in your area",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    Spacer(modifier = Modifier.weight(1f))

                    Icon(
                        imageVector = Icons.Default.ChevronRight,
                        contentDescription = "View details",
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(16.dp))

            // Quick Actions
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(
                    onClick = { /* Post a service */ },
                    modifier = Modifier.weight(1f)
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = null,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Post Service")
                }

                OutlinedButton(
                    onClick = { /* Find services */ },
                    modifier = Modifier.weight(1f)
                ) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = null,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Find Services")
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
