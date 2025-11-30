package com.example.micalendariov4.view.tools

import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

enum class WindowSize {
    COMPACT,
    MEDIUM,
    EXPANDED
}

@Composable
fun getWindowSizeClass(maxWidth: Dp): WindowSize = when {
    maxWidth < 1000.dp -> WindowSize.COMPACT
    maxWidth < 1500.dp -> WindowSize.MEDIUM
    else -> WindowSize.EXPANDED
}