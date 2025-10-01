package com.example.handyhood

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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

data class Message(
    val senderName: String,
    val message: String,
    val time: String,
    val isRead: Boolean,
    val serviceType: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InboxScreen(modifier: Modifier = Modifier) {
    val messages = listOf(
        Message("John Smith", "Hi! I'm interested in your house cleaning service.", "2 hours ago", false, "House Cleaning"),
        Message("Sarah Wilson", "Thanks for the great plumbing work yesterday!", "5 hours ago", true, "Plumbing"),
        Message("Mike Brown", "Are you available for dog walking this weekend?", "1 day ago", false, "Pet Care"),
        Message("Lisa Johnson", "The garden looks amazing! Thank you so much.", "2 days ago", true, "Gardening"),
        Message("Tom Davis", "Can we schedule the repair for next Tuesday?", "3 days ago", true, "Repairs")
    )

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        item {
            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Messages",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold
                )

                IconButton(onClick = { /* Mark all as read */ }) {
                    Icon(
                        Icons.Default.MarkEmailRead,
                        contentDescription = "Mark all as read"
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Message stats
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                ElevatedCard(
                    modifier = Modifier.weight(1f)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            Icons.Default.Inbox,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary
                        )
                        Text(
                            text = "${messages.size}",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "Total",
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }

                ElevatedCard(
                    modifier = Modifier.weight(1f)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            Icons.Default.MarkEmailUnread,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.error
                        )
                        Text(
                            text = "${messages.count { !it.isRead }}",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "Unread",
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Recent Messages",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.SemiBold
            )
        }

        items(messages) { message ->
            MessageCard(
                message = message,
                onClick = { /* Handle message click */ }
            )
        }

        item {
            Spacer(modifier = Modifier.height(80.dp)) // Bottom padding for navigation
        }
    }
}

@Composable
fun MessageCard(
    message: Message,
    onClick: () -> Unit
) {
    ElevatedCard(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.elevatedCardColors(
            containerColor = if (!message.isRead) {
                MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.1f)
            } else {
                MaterialTheme.colorScheme.surface
            }
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.Top
        ) {
            // Avatar/Icon
            Card(
                modifier = Modifier.size(48.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            ) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = message.senderName.split(" ").map { it.first() }.joinToString(""),
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onPrimary,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = message.senderName,
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = if (!message.isRead) FontWeight.Bold else FontWeight.Medium
                    )

                    Text(
                        text = message.time,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = message.serviceType,
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.primary
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = message.message,
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 2,
                    color = if (!message.isRead) {
                        MaterialTheme.colorScheme.onSurface
                    } else {
                        MaterialTheme.colorScheme.onSurfaceVariant
                    }
                )
            }

            if (!message.isRead) {
                Box(
                    modifier = Modifier
                        .size(8.dp)
                        .padding(top = 4.dp)
                ) {
                    Card(
                        modifier = Modifier.fillMaxSize(),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.primary
                        )
                    ) {}
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun InboxScreenPreview() {
    HandyHoodTheme {
        InboxScreen()
    }
}
