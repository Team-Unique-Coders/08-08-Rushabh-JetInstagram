package com.vipulasri.jetinstagram

import android.Manifest
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import com.vipulasri.jetinstagram.ui.MainScreen
import com.vipulasri.jetinstagram.ui.theme.JetInstagramTheme

class MainActivity : AppCompatActivity() {

  @ExperimentalFoundationApi
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    WindowCompat.setDecorFitsSystemWindows(window, false)

    this.setContent {
      JetInstagramTheme {
        Surface(color = MaterialTheme.colors.background) {
          MainScreen()
        }
      }
    }
  }

}