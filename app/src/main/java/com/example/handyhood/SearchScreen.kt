package com.example.handyhood.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.handyhood.ui.theme.HandyHoodTheme

data class SearchResult(
    val id: Int,
    val title: String,
    val description: String,
    val category: String,
    val rating: Float,
    val distance: String,
    val price: String? = null
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    modifier: Modifier = Modifier
) {
    var searchQuery by remember { mutableStateOf("") }
    var showFilters by remember { mutableStateOf(false) }
    var selectedCategory by remember { mutableStateOf("All") }
    var sortBy by remember { mutableStateOf("Relevance") }

    val filterIconRotation by animateFloatAsState(
        targetValue = if (showFilters) 180f else 0f,
        label = "filterIconRotation"
    )

    val categories = listOf("All", "Services", "Events", "For Sale", "Lost & Found", "Recommendations")
    val sortOptions = listOf("Relevance", "Distance", "Rating", "Price: Low to High", "Price: High to Low")

    val sampleResults = remember {
        listOf(
            SearchResult(1, "Professional House Cleaning", "Reliable cleaning service with great reviews", "Services", 4.8f, "0.3 miles", "$50/hour"),
            SearchResult(2, "Guitar Lessons", "Learn guitar with experienced instructor", "Services", 4.9f, "0.5 miles", "$40/hour"),
            SearchResult(3, "Community Yoga Class", "Weekly yoga sessions in the park", "Events", 4.7f, "0.7 miles"),
            SearchResult(4, "Baby Stroller - Like New", "Barely used stroller, excellent condition", "For Sale", 4.5f, "1.2 miles", "$120"),
            SearchResult(5, "Lost Dog - Golden Retriever", "Missing since yesterday, very friendly", "Lost & Found", 0f, "0.8 miles"),
        )
    }.filter {
        (selectedCategory == "All" || it.category == selectedCategory) &&
                (searchQuery.isEmpty() || it.title.contains(searchQuery, ignoreCase = true) ||
                        it.description.contains(searchQuery, ignoreCase = true))
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Search Header
        Text(
            text = "Search HandyHood",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Search Bar
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("Search for services, events, items...") },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search"
                )
            },
            trailingIcon = {
                if (searchQuery.isNotEmpty()) {
                    IconButton(onClick = { searchQuery = "" }) {
                        Icon(
                            imageVector = Icons.Default.Clear,
                            contentDescription = "Clear"
                        )
                    }
                }
            },
            singleLine = true,
            shape = RoundedCornerShape(12.dp)
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Filter and Sort Row
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            FilterChip(
                onClick = { showFilters = !showFilters },
                label = { Text("Filters") },
                selected = showFilters,
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.FilterList,
                        contentDescription = "Filters",
                        modifier = Modifier.rotate(filterIconRotation)
                    )
                }
            )

            AssistChip(
                onClick = { /* Show sort options */ },
                label = { Text("Sort: $sortBy") },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Sort,
                        contentDescription = "Sort"
                    )
                }
            )

            Spacer(modifier = Modifier.weight(1f))

            Text(
                text = "${sampleResults.size} results",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        // Filters Panel
        AnimatedVisibility(visible = showFilters) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                )
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "Categories",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Medium
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(categories) { category ->
                            FilterChip(
                                onClick = { selectedCategory = category },
                                label = { Text(category) },
                                selected = selectedCategory == category
                            )
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Results List
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(sampleResults) { result ->
                SearchResultCard(
                    result = result,
                    onClick = { /* Navigate to details */ }
                )
            }

            if (sampleResults.isEmpty() && searchQuery.isNotEmpty()) {
                item {
                    EmptySearchState()
                }
            }
        }
    }
}

@Composable
fun SearchResultCard(
    result: SearchResult,
    onClick: () -> Unit
) {
    ElevatedCard(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = result.title,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = result.description,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                result.price?.let { price ->
                    Text(
                        text = price,
                        style = MaterialTheme.typography.titleSmall,
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    AssistChip(
                        onClick = { },
                        label = {
                            Text(
                                text = result.category,
                                style = MaterialTheme.typography.labelSmall
                            )
                        },
                        modifier = Modifier.height(24.dp)
                    )

                    if (result.rating > 0) {
                        Spacer(modifier = Modifier.width(8.dp))
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Star,
                                contentDescription = "Rating",
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = result.rating.toString(),
                                style = MaterialTheme.typography.labelSmall
                            )
                        }
                    }
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.LocationOn,
                        contentDescription = "Distance",
                        tint = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = result.distance,
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}

@Composable
fun EmptySearchState() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = Icons.Default.SearchOff,
            contentDescription = "No results",
            modifier = Modifier.size(64.dp),
            tint = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "No results found",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Try adjusting your search terms or filters",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SearchScreenPreview() {
    HandyHoodTheme {
        SearchScreen()
    }
}