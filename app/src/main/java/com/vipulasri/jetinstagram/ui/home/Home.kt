package com.vipulasri.jetinstagram.ui.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.vipulasri.jetinstagram.R
import com.vipulasri.jetinstagram.data.PostsRepository
import com.vipulasri.jetinstagram.data.StoriesRepository
import com.vipulasri.jetinstagram.model.Post
import com.vipulasri.jetinstagram.model.Story
import com.vipulasri.jetinstagram.ui.components.icon
import kotlinx.coroutines.launch

@ExperimentalFoundationApi
@Composable
fun Home() {
  val listState = rememberLazyListState()
  val coroutineScope = rememberCoroutineScope()

  // Keep track of previous scroll offset for direction detection
  var previousOffset by remember { mutableStateOf(0) }
  var previousIndex by remember { mutableStateOf(0) }

  // Scroll direction state: true means scrolling up or at top (show toolbar)
  var isScrollingUp by remember { mutableStateOf(true) }

  // Observe scroll changes and detect direction
  LaunchedEffect(listState) {
    snapshotFlow { listState.firstVisibleItemIndex to listState.firstVisibleItemScrollOffset }
      .collect { (index, offset) ->
        // Compare current position to previous position
        isScrollingUp = when {
          index < previousIndex -> true // Scrolled up to earlier items
          index > previousIndex -> false // Scrolled down to later items
          else -> offset < previousOffset // Same item, compare offset
        }
        previousIndex = index
        previousOffset = offset
      }
  }

  val posts by PostsRepository.posts
  val stories by StoriesRepository.observeStories()

  Column {
    AnimatedVisibility(
      visible = isScrollingUp,
      enter = slideInVertically(initialOffsetY = { -it }),
      exit = slideOutVertically(targetOffsetY = { -it }),
    ) {
      Toolbar()
    }

    LazyColumn(state = listState) {
      item {
        StoriesSection(stories)
        Spacer(Modifier.height(8.dp))
      }
      itemsIndexed(posts) { _, post ->
        Post(
          post = post,
          onDoubleClick = {
            coroutineScope.launch {
              PostsRepository.performLike(post.id)
            }
          },
          onLikeToggle = {
            coroutineScope.launch {
              PostsRepository.toggleLike(post.id)
            }
          }
        )
      }
    }
  }
}

@Composable
private fun Toolbar() {
  Row(
    modifier = Modifier
      .fillMaxWidth()
      .height(56.dp)
      .padding(horizontal = 10.dp),
    horizontalArrangement = Arrangement.SpaceBetween,
    verticalAlignment = Alignment.CenterVertically,
  ) {
    Icon(
      imageVector = ImageVector.vectorResource(id = R.drawable.ic_instagram),
      contentDescription = null
    )
    Icon(
      bitmap = ImageBitmap.imageResource(id = R.drawable.ic_dm),
      modifier = Modifier.icon(),
      contentDescription = null
    )
  }
}

@Composable
private fun StoriesSection(stories: List<Story>) {
  Column {
    StoriesList(stories)
    Spacer(modifier = Modifier.height(10.dp))
  }
}

@Composable
private fun StoriesList(stories: List<Story>) {
  androidx.compose.foundation.lazy.LazyRow {
    itemsIndexed(stories) { index, story ->

      if (index == 0) {
        Spacer(modifier = Modifier.width(6.dp))
      }

      Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(vertical = 5.dp, horizontal = 6.dp)
      ) {
        StoryImage(imageUrl = story.image)
        Spacer(modifier = Modifier.height(5.dp))
        Text(story.name, style = MaterialTheme.typography.caption)
      }

      if (index == stories.size - 1) {
        Spacer(modifier = Modifier.width(6.dp))
      }
    }
  }
}

@ExperimentalFoundationApi
@Composable
private fun Post(
  post: Post,
  onDoubleClick: (Post) -> Unit,
  onLikeToggle: (Post) -> Unit,
) {
  PostView(post, onDoubleClick, onLikeToggle)
}
