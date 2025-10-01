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
fun SearchScreen(modifier: Modifier = Modifier) {
    var searchQuery by remember { mutableStateOf("") }
    val filterOptions = listOf("All", "Nearby", "Highest Rated", "Recent", "Price: Low to High")
    val searchResults = listOf(
        "Professional House Cleaning" to "Available Today",
        "Expert Plumbing Services" to "24/7 Emergency",
        "Dog Walking & Pet Care" to "Trusted Sitters",
        "Garden & Lawn Maintenance" to "Weekly Service"
    )

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            // Search Header
            Text(
                text = "Find Services",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Search Bar
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("Search for services...") },
                leadingIcon = {
                    Icon(
                        Icons.Default.Search,
                        contentDescription = "Search"
                    )
                },
                trailingIcon = {
                    if (searchQuery.isNotEmpty()) {
                        IconButton(onClick = { searchQuery = "" }) {
                            Icon(
                                Icons.Default.Clear,
                                contentDescription = "Clear"
                            )
                        }
                    }
                }
            )
        }

        item {
            // Filter Section
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    Icons.Default.FilterList,
                    contentDescription = "Filter",
                    tint = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Filter by:",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Medium
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(filterOptions) { option ->
                    FilterChip(
                        onClick = { /* Handle filter selection */ },
                        label = { Text(option) },
                        selected = option == "All",
                        leadingIcon = if (option == "Nearby") {
                            {
                                Icon(
                                    Icons.Default.LocationOn,
                                    contentDescription = null,
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                        } else null
                    )
                }
            }
        }

        item {
            // Results Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = if (searchQuery.isEmpty()) "Popular Services" else "Search Results",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.SemiBold
                )

                TextButton(
                    onClick = { /* Sort options */ }
                ) {
                    Icon(
                        Icons.Default.Sort,
                        contentDescription = "Sort",
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Sort")
                }
            }
        }

        if (searchQuery.isEmpty()) {
            items(searchResults) { (serviceName, availability) ->
                ServiceResultCard(
                    serviceName = serviceName,
                    availability = availability,
                    onClick = { /* Handle service selection */ }
                )
            }
        } else {
            item {
                // Search results or no results state
                if (searchResults.any { it.first.contains(searchQuery, ignoreCase = true) }) {
                    // Show filtered results
                    searchResults.filter { it.first.contains(searchQuery, ignoreCase = true) }
                        .forEach { (serviceName, availability) ->
                            ServiceResultCard(
                                serviceName = serviceName,
                                availability = availability,
                                onClick = { /* Handle service selection */ }
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                        }
                } else {
                    // No results state
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(32.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            Icons.Default.SearchOff,
                            contentDescription = null,
                            modifier = Modifier.size(64.dp),
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "No services found",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Text(
                            text = "Try adjusting your search terms",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ServiceResultCard(
    serviceName: String,
    availability: String,
    onClick: () -> Unit
) {
    ElevatedCard(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                Icons.Default.Star,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(24.dp)
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = serviceName,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = availability,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.primary
                )
            }

            Icon(
                Icons.Default.ChevronRight,
                contentDescription = "View details",
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SearchScreenPreview() {
    HandyHoodTheme {
        SearchScreen()
    }
}
