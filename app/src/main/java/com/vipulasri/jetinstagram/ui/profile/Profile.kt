package com.vipulasri.jetinstagram.ui.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.TabRowDefaults
import androidx.compose.material.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.vipulasri.jetinstagram.R
import com.vipulasri.jetinstagram.data.ReelsRepository
import com.vipulasri.jetinstagram.ui.components.VideoPlayer

@Composable
fun ProfileStat(count: String, label: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(count, style = MaterialTheme.typography.h6)
        Text(label, style = MaterialTheme.typography.body2)
    }
}

@Composable
fun Profile() {
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        // Fake data
        item {
            // 1️⃣ Username + Button
            UsernameSection()

            // 2️⃣ Profile photo, name, followers
            ProfileHeaderSection()

            // 3️⃣ Description
            DescriptionSection()

            // 4️⃣ Edit / Share buttons
            ActionButtonsSection()

            // 5️⃣ Highlights
            HighlightsSection()
        }

        // 6️⃣ Tabs + Content
        item {
            ProfileTabsSection()
        }
    }
}


@Composable
fun UsernameSection(){
    Row(
        Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text("john_doe", style = MaterialTheme.typography.h6)
        IconButton(onClick = { /* Menu Click */ }) {
            Icon(Icons.Default.MoreVert, contentDescription = "Menu")
        }
    }
}


@OptIn(ExperimentalCoilApi::class)
@Composable
fun ProfileHeaderSection(){
    val profileImage = "https://randomuser.me/api/portraits/men/14.jpg"
    Row(
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = rememberImagePainter(profileImage),
            contentDescription = null,
            modifier = Modifier
                .size(90.dp)
                .clip(CircleShape)
        )

        Spacer(Modifier.width(16.dp))

        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            ProfileStat("152", "Posts")
            ProfileStat("2.5K", "Followers")
            ProfileStat("310", "Following")
        }
    }
}

@Composable
fun DescriptionSection(){
    Column(Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
        Text("John Doe", style = MaterialTheme.typography.subtitle1)
        Text("📷 Photographer | ☕ Coffee Lover\n🌍 Exploring the world", style = MaterialTheme.typography.body2)
    }
}

@Composable
fun ActionButtonsSection(){
    Row(
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        OutlinedButton(
            onClick = { },
            modifier = Modifier.weight(1f)
        ) {
            Text("Edit Profile")
        }
        OutlinedButton(
            onClick = { },
            modifier = Modifier.weight(1f)
        ) {
            Text("Share Profile")
        }
    }
}
// Highlights Section
@Composable
fun HighlightsSection(){
    val highlights = List(10) { "Highlight ${it + 1}" }
        LazyRow(
            Modifier
                .padding(vertical = 12.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(horizontal = 16.dp)
        ) {
            items(highlights.size) { index ->
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Box(
                        Modifier
                            .size(70.dp)
                            .clip(CircleShape)
                            .background(Color.LightGray),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = rememberImagePainter("https://picsum.photos/100/100?random=$index"),
                            contentDescription = null,
                            modifier = Modifier
                                .clip(CircleShape)
                                .matchParentSize(),
                            contentScale = ContentScale.Crop // ensures full coverage
                        )
                    }
                    Spacer(Modifier.height(4.dp))
                    Text(highlights[index], style = MaterialTheme.typography.caption)
                }
            }
        }
}


//Profile Tabs Section

@Composable
fun ProfileTabsSection() {
    val tabs = listOf(
        TabItem(
            icon = R.drawable.ic_outlined_favorite,    // your "posts" icon
            content = { PostsGridContent() }
        ),
        TabItem(
            icon = R.drawable.ic_filled_reels,   // your "reels" icon
            content = { ReelsGridContent() }
        ),
        TabItem(
            icon = R.drawable.ic_outlined_bookmark,     // your "tagged" icon
            content = { TaggedGridContent() }
        )
    )

    var selectedTabIndex by remember { mutableStateOf(0) }

    Column {
        TabRow(
            selectedTabIndex = selectedTabIndex,
            backgroundColor = Color.White,
            contentColor = Color.LightGray, // indicator & icon tint
            indicator = { tabPositions ->
                TabRowDefaults.Indicator(
                    Modifier
                        .tabIndicatorOffset(tabPositions[selectedTabIndex]),
                    color = Color.LightGray
                )
            }
        ) {
            tabs.forEachIndexed { index, tabItem ->
                Tab(
                    selected = selectedTabIndex == index,
                    onClick = { selectedTabIndex = index },
                    icon = {
                        Icon(
                            painter = painterResource(id = tabItem.icon),
                            contentDescription = null,
                            tint = if (selectedTabIndex == index) Color.Black else Color.LightGray
                        )
                    }
                )
            }
        }

        // Tab content
        tabs[selectedTabIndex].content()
    }
}

data class TabItem(
    val icon: Int,
    val content: @Composable () -> Unit
)

@Composable
fun PostsGridContent() {
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        modifier = Modifier.fillMaxSize().heightIn(max = 2000.dp) ,
        content = {
            items(30) { index ->
                Image(
                    painter = rememberImagePainter("https://picsum.photos/300/300?random=$index"),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .aspectRatio(1f)
                        .padding(1.dp)
                )
            }
        }
    )
}

@Composable
fun ReelsGridContent() {
    val reels = ReelsRepository.getReels()
    LazyVerticalGrid( columns = GridCells.Fixed(3),
        modifier = Modifier.fillMaxSize().heightIn(max = 2000.dp) ,
        content = {
            items(reels.size/3) { index ->
                VideoPlayer(uri = reels.get(index).getVideoUrl())
            }
        }
    )
}

@Composable
fun TaggedGridContent() {
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        modifier = Modifier.fillMaxSize().heightIn(max = 2000.dp) ,
        content = {
            items(15) { index ->
                Box(
                    modifier = Modifier
                        .aspectRatio(1f)
                        .background(Color.Gray.copy(alpha = 0.3f)),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Tag $index")
                }
            }
        }
    )
}
