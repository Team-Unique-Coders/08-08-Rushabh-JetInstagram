package com.vipulasri.jetinstagram.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.vipulasri.jetinstagram.R
import com.vipulasri.jetinstagram.model.currentUser
import com.vipulasri.jetinstagram.ui.HomeSection.Add
import com.vipulasri.jetinstagram.ui.HomeSection.Favorite
import com.vipulasri.jetinstagram.ui.HomeSection.Home
import com.vipulasri.jetinstagram.ui.HomeSection.Profile
import com.vipulasri.jetinstagram.ui.HomeSection.Reels
import com.vipulasri.jetinstagram.ui.components.bottomBarHeight
import com.vipulasri.jetinstagram.ui.components.icon
import com.vipulasri.jetinstagram.ui.home.Home
import com.vipulasri.jetinstagram.ui.reels.Reels
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

@ExperimentalFoundationApi
@Composable
fun MainScreen() {

    val sectionState = remember { mutableStateOf(Home) }

    val navItems = HomeSection.values()
      .toList()

    // Shared scroll state
    val listState = rememberLazyListState()
    var isScrollingUp by remember { mutableStateOf(true) }
    var lastScrollOffset by remember { mutableStateOf(0) }

    LaunchedEffect(listState.firstVisibleItemScrollOffset) {
        val currentOffset = listState.firstVisibleItemScrollOffset
        isScrollingUp = currentOffset < lastScrollOffset
        lastScrollOffset = currentOffset
    }

    Scaffold(
        bottomBar = {
            AnimatedVisibility(
                visible = isScrollingUp,
                enter = slideInVertically(initialOffsetY = { it }),
                exit = slideOutVertically(targetOffsetY = { it })
            ) {
                BottomBar(
                    items = navItems,
                    currentSection = sectionState.value,
                    onSectionSelected = { sectionState.value = it }
                )
            }
        }
    ) { innerPadding ->
    val modifier = Modifier.padding(innerPadding)
        .windowInsetsPadding(WindowInsets.statusBars.only(WindowInsetsSides.Top))
    Crossfade(
        modifier = modifier,
        targetState = sectionState.value)
    { section ->
        when (section) {
            Home -> Home(listState = listState, isScrollingUp = isScrollingUp)
            Reels -> Reels()
            Add -> Content(title = "Add Post options")
            Favorite -> Content(title = "Favorite")
            Profile -> Content(title = "Profile")
        }
    }
  }
}

@Composable
private fun Content(title: String) {
  Box(
      modifier = Modifier.fillMaxSize(),
      contentAlignment = Alignment.Center
  ) {
    Text(
        text = title,
        textAlign = TextAlign.Center,
        style = MaterialTheme.typography.h5
    )
  }
}

@Composable
private fun BottomBar(
  items: List<HomeSection>,
  currentSection: HomeSection,
  onSectionSelected: (HomeSection) -> Unit,
) {
  BottomNavigation(
      modifier = Modifier.height(bottomBarHeight),
      backgroundColor = MaterialTheme.colors.background,
      contentColor = contentColorFor(MaterialTheme.colors.background)
  ) {
    items.forEach { section ->

      val selected = section == currentSection

      val iconRes = if (selected) section.selectedIcon else section.icon

      BottomNavigationItem(
          modifier = Modifier.padding(bottom= 12.dp),
          icon = {
            if (section == Profile) {
              BottomBarProfile(selected)
            } else {
              Icon(
                  ImageBitmap.imageResource(id = iconRes),
                  modifier = Modifier.icon(),
                  contentDescription = ""
              )
            }

          },
          selected = selected,
          onClick = { onSectionSelected(section) },
          alwaysShowLabel = false
      )
    }
  }
}

@Composable
private fun BottomBarProfile(isSelected: Boolean) {
  val shape = CircleShape

  val borderModifier = if (isSelected) {
    Modifier
        .border(
            color = Color.LightGray,
            width = 1.dp,
            shape = shape
        )
  } else Modifier

  val padding = if (isSelected) 3.dp else 0.dp

  Box(
      modifier = borderModifier
  ) {
    Box(
        modifier = Modifier.icon()
            .padding(padding)
            .background(color = Color.LightGray, shape = shape)
            .clip(shape)
    ) {
        Image(
            painter = rememberImagePainter(currentUser.image),
            contentDescription = null,
            modifier = Modifier.fillMaxSize()
        )
    }
  }

}

private enum class HomeSection(
  val icon: Int,
  val selectedIcon: Int
) {
  Home(R.drawable.ic_outlined_home, R.drawable.ic_filled_home),
  Reels(R.drawable.ic_outlined_reels, R.drawable.ic_filled_reels),
  Add(R.drawable.ic_outlined_add, R.drawable.ic_outlined_add),
  Favorite(R.drawable.ic_outlined_favorite, R.drawable.ic_filled_favorite),
  Profile(R.drawable.ic_outlined_reels, R.drawable.ic_outlined_reels)
}

