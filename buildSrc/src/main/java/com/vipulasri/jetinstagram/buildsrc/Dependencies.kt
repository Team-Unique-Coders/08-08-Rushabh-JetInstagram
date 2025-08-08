package com.vipulasri.jetinstagram.buildsrc

object Libs {
  // Updated AGP to stable 8.1.1
  const val androidGradlePlugin = "com.android.tools.build:gradle:8.1.1"
  const val junit = "junit:junit:4.13"

  const val exoplayer = "com.google.android.exoplayer:exoplayer:2.18.5"

  object Coil {
    private const val version = "1.3.2"
    const val sdk = "io.coil-kt:coil-compose:$version"
  }

  object Kotlin {
    // Updated Kotlin to recent stable 1.9.10
    private const val version = "1.9.10"
    const val stdlib = "org.jetbrains.kotlin:kotlin-stdlib:$version"
    const val gradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:$version"
    const val extensions = "org.jetbrains.kotlin:kotlin-android-extensions:$version"
  }

  object Coroutines {
    private const val version = "1.5.2"
    const val core = "org.jetbrains.kotlinx:kotlinx-coroutines-core:$version"
    const val android = "org.jetbrains.kotlinx:kotlinx-coroutines-android:$version"
    const val test = "org.jetbrains.kotlinx:kotlinx-coroutines-test:$version"
  }

  object AndroidX {
    const val appcompat = "androidx.appcompat:appcompat:1.3.0-alpha01"
    const val coreKtx = "androidx.core:core-ktx:1.5.0-alpha02"
    const val material = "com.google.android.material:material:1.2.0"
    const val tooling = "androidx.ui:ui-tooling:1.0.0-alpha07"

    object Compose {
      const val snapshot = ""
      const val version = "1.5.3"
      const val activityComposeVersion = "1.7.2"

      const val runtime = "androidx.compose.runtime:runtime:$version"
      const val compiler = "androidx.compose.compiler:compiler:$version"
      const val foundation = "androidx.compose.foundation:foundation:$version"
      const val layout = "androidx.compose.foundation:foundation-layout:$version"
      const val ui = "androidx.compose.ui:ui:$version"
      const val tooling = "androidx.compose.ui:ui-tooling:$version"
      const val material = "androidx.compose.material:material:$version"
      const val animation = "androidx.compose.animation:animation:$version"
      const val activity = "androidx.activity:activity-compose:$activityComposeVersion"
    }

    object Test {
      private const val version = "1.2.0"
      const val core = "androidx.test:core:$version"
      const val rules = "androidx.test:rules:$version"

      object Ext {
        private const val version = "1.1.2-rc01"
        const val junit = "androidx.test.ext:junit-ktx:$version"
      }

      const val espressoCore = "androidx.test.espresso:espresso-core:3.2.0"
    }
  }
}
