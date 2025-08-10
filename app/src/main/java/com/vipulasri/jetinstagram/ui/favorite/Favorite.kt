package com.vipulasri.jetinstagram.ui.favorite

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.vipulasri.jetinstagram.data.StoriesRepository
import com.vipulasri.jetinstagram.model.Story

@Composable
fun Favorites() {
    val stories by StoriesRepository.observeStories()

    // Fake events
    val todayHistory = listOf(
        ActivityEvent(stories[1], "liked your photo", "https://picsum.photos/200?random=1"),
        ActivityEvent(stories[2], "commented: Awesome!", "https://picsum.photos/200?random=2"),
        ActivityEvent(stories[3], "started following you", null)
    )
    val last7DaysHistory = listOf(
        ActivityEvent(stories[4], "liked your photo", "https://picsum.photos/200?random=3"),
        ActivityEvent(stories[5], "commented: 🔥🔥🔥", "https://picsum.photos/200?random=4")
    )
    val last30DaysHistory = listOf(
        ActivityEvent(stories[6], "started following you", null),
        ActivityEvent(stories[7], "liked your photo", "https://picsum.photos/200?random=5")
    )

    val suggestedUsers = stories.takeLast(5)

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Pending Requests
        item {
            PendingRequestsSection(count = 3)
        }

        // Today
        if (todayHistory.isNotEmpty()) {
            item { SectionHeader("Today") }
            items(todayHistory) { event ->
                ActivityItem(event)
            }
        }

        // Last 7 days
        if (last7DaysHistory.isNotEmpty()) {
            item { SectionHeader("Last 7 Days") }
            items(last7DaysHistory) { event ->
                ActivityItem(event)
            }
        }

        // Last 30 days
        if (last30DaysHistory.isNotEmpty()) {
            item { SectionHeader("Last 30 Days") }
            items(last30DaysHistory) { event ->
                ActivityItem(event)
            }
        }

        // Suggested for You
        item { SectionHeader("Suggested for You") }
        item {
            SuggestedUsersRow(suggestedUsers)
        }
    }
}

data class ActivityEvent(
    val user: Story,
    val action: String,
    val postImageUrl: String? // null if no image
)

@Composable
fun ActivityItem(event: ActivityEvent) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = rememberImagePainter(event.user.image),
            contentDescription = null,
            modifier = Modifier
                .size(50.dp)
                .clip(CircleShape)
        )
        Spacer(Modifier.width(12.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                buildAnnotatedString {
                    withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
                        append(event.user.name)
                    }
                    append(" ${event.action}")
                },
                style = MaterialTheme.typography.body2
            )
            Text("2h", style = MaterialTheme.typography.caption)
        }
        if (event.postImageUrl != null) {
            Image(
                painter = rememberImagePainter(event.postImageUrl),
                contentDescription = null,
                modifier = Modifier
                    .size(50.dp)
                    .clip(RoundedCornerShape(6.dp))
            )
        }
    }
}

@Composable
fun PendingRequestsSection(count: Int) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = 2.dp
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("$count follow requests", fontWeight = FontWeight.Bold)
            Icon(Icons.Default.ArrowForward, contentDescription = null)
        }
    }
}

@Composable
fun SectionHeader(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.subtitle2.copy(fontWeight = FontWeight.Bold),
        color = Color.Gray,
        modifier = Modifier.padding(vertical = 4.dp)
    )
}

@Composable
fun SuggestedUsersRow(users: List<Story>) {
    LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
        items(users) { user ->
            SuggestedUserCard(user)
        }
    }
}

@Composable
fun SuggestedUserCard(user: Story) {
    Card(
        modifier = Modifier.width(120.dp),
        elevation = 3.dp
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(8.dp)
        ) {
            Image(
                painter = rememberImagePainter(user.image),
                contentDescription = null,
                modifier = Modifier
                    .size(60.dp)
                    .clip(CircleShape)
            )
            Spacer(Modifier.height(8.dp))
            Text(user.name, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(4.dp))
            Button(
                onClick = { /* follow */ },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFFE0E0E0))
            ) {
                Text("Follow", color = Color.Black)
            }
        }
    }
}
