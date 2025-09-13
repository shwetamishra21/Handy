package com.handyhood.app.ui.screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.handyhood.app.ui.theme.HandyHoodTheme
import java.text.SimpleDateFormat
import java.util.*

data class Message(
    val id: Int,
    val senderName: String,
    val subject: String,
    val preview: String,
    val timestamp: String,
    val isRead: Boolean,
    val messageType: MessageType
)

enum class MessageType {
    DIRECT, COMMUNITY, NOTIFICATION
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InboxScreen(
    modifier: Modifier = Modifier
) {
    var selectedTab by remember { mutableStateOf(0) }
    val tabs = listOf("All", "Direct", "Community", "Notifications")

    val sampleMessages = remember {
        listOf(
            Message(1, "Sarah M.", "About Fluffy", "Thank you so much for helping me find...", "2h ago", false, MessageType.DIRECT),
            Message(2, "HandyHood Team", "Welcome to HandyHood!", "Welcome to your neighborhood community...", "1d ago", true, MessageType.NOTIFICATION),
            Message(3, "Mike Johnson", "BBQ Reminder", "Just a friendly reminder about the community...", "3h ago", false, MessageType.COMMUNITY),
            Message(4, "Tom Builder", "Service Inquiry", "Hi! I saw your post about needing a handyman...", "5h ago", true, MessageType.DIRECT),
            Message(5, "Community Board", "New Event Posted", "A new event has been posted in your area...", "1d ago", false, MessageType.NOTIFICATION),
            Message(6, "Emma Wilson", "Piano Pickup", "Hi! I'm interested in the free piano you posted...", "2d ago", true, MessageType.DIRECT),
        )
    }

    val filteredMessages = when (selectedTab) {
        1 -> sampleMessages.filter { it.messageType == MessageType.DIRECT }
        2 -> sampleMessages.filter { it.messageType == MessageType.COMMUNITY }
        3 -> sampleMessages.filter { it.messageType == MessageType.NOTIFICATION }
        else -> sampleMessages
    }

    val unreadCount = sampleMessages.count { !it.isRead }

    Column(
        modifier = modifier.fillMaxSize()
    ) {
        // Header
        TopAppBar(
            title = {
                Column {
                    Text(
                        text = "Inbox",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold
                    )
                    if (unreadCount > 0) {
                        Text(
                            text = "$unreadCount unread messages",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            },
            actions = {
                IconButton(onClick = { /* Mark all as read */ }) {
                    Icon(
                        imageVector = Icons.Default.MarkEmailRead,
                        contentDescription = "Mark all as read"
                    )
                }
                IconButton(onClick = { /* Search messages */ }) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search"
                    )
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant
            )
        )

        // Tabs
        TabRow(
            selectedTabIndex = selectedTab,
            modifier = Modifier.padding(horizontal = 16.dp)
        ) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTab == index,
                    onClick = { selectedTab = index },
                    text = {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(title)
                            if (index == 0 && unreadCount > 0) {
                                Spacer(modifier = Modifier.width(8.dp))
                                Badge {
                                    Text(unreadCount.toString())
                                }
                            }
                        }
                    }
                )
            }
        }

        // Messages List
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(filteredMessages) { message ->
                MessageCard(
                    message = message,
                    onClick = { /* Open message */ },
                    onMarkAsRead = { /* Mark as read */ },
                    onDelete = { /* Delete message */ }
                )
            }

            if (filteredMessages.isEmpty()) {
                item {
                    EmptyInboxState()
                }
            }
        }
    }
}

@Composable
fun MessageCard(
    message: Message,
    onClick: () -> Unit,
    onMarkAsRead: () -> Unit,
    onDelete: () -> Unit
) {
    var isPressed by remember { mutableStateOf(false) }
    val cardScale by animateFloatAsState(
        targetValue = if (isPressed) 0.98f else 1f,
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy)
    )

    ElevatedCard(
        onClick = {
            isPressed = true
            onClick()
        },
        modifier = Modifier
            .fillMaxWidth()
            .scale(cardScale),
        elevation = CardDefaults.elevatedCardElevation(
            defaultElevation = if (!message.isRead) 4.dp else 2.dp
        ),
        colors = CardDefaults.elevatedCardColors(
            containerColor = if (!message.isRead)
                MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.1f)
            else
                MaterialTheme.colorScheme.surface
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.Top
        ) {
            // Unread Indicator
            if (!message.isRead) {
                Box(
                    modifier = Modifier
                        .size(12.dp)
                        .background(
                            MaterialTheme.colorScheme.primary,
                            CircleShape
                        )
                )
                Spacer(modifier = Modifier.width(12.dp))
            } else {
                Spacer(modifier = Modifier.width(24.dp))
            }

            // Avatar
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(
                        when (message.messageType) {
                            MessageType.DIRECT -> MaterialTheme.colorScheme.primary
                            MessageType.COMMUNITY -> MaterialTheme.colorScheme.secondary
                            MessageType.NOTIFICATION -> MaterialTheme.colorScheme.tertiary
                        }
                    ),
                contentAlignment = Alignment.Center
            ) {
                val icon = when (message.messageType) {
                    MessageType.DIRECT -> Icons.Default.Person
                    MessageType.COMMUNITY -> Icons.Default.Group
                    MessageType.NOTIFICATION -> Icons.Default.Notifications
                }
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.size(20.dp)
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            // Content
            Column(modifier = Modifier.weight(1f)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Top
                ) {
                    Text(
                        text = message.senderName,
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = if (!message.isRead) FontWeight.Bold else FontWeight.Medium,
                        modifier = Modifier.weight(1f)
                    )
                    Text(
                        text = message.timestamp,
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = message.subject,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = if (!message.isRead) FontWeight.SemiBold else FontWeight.Normal,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(2.dp))

                Text(
                    text = message.preview,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }

            // Actions
            Column {
                IconButton(
                    onClick = onMarkAsRead,
                    modifier = Modifier.size(24.dp)
                ) {
                    Icon(
                        imageVector = if (message.isRead) Icons.Default.MarkEmailUnread else Icons.Default.MarkEmailRead,
                        contentDescription = if (message.isRead) "Mark as unread" else "Mark as read",
                        modifier = Modifier.size(16.dp)
                    )
                }
            }
        }
    }

    LaunchedEffect(isPressed) {
        if (isPressed) {
            kotlinx.coroutines.delay(100)
            isPressed = false
        }
    }
}

@Composable
fun EmptyInboxState() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = Icons.Default.Inbox,
            contentDescription = "Empty inbox",
            modifier = Modifier.size(64.dp),
            tint = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "No messages yet",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "When you receive messages, they'll appear here",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Preview(showBackground = true)
@Composable
fun InboxScreenPreview() {
    HandyHoodTheme {
        InboxScreen()
    }
}
