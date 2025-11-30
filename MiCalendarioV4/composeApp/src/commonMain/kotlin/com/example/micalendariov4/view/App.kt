package com.example.micalendariov4.view

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import com.example.micalendariov4.view.layouts.ExpandedLayout
import com.example.micalendariov4.view.tools.WindowSize
import com.example.micalendariov4.view.tools.getWindowSizeClass
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    MaterialTheme {
        BoxWithConstraints {
            val width = this.maxWidth
            when(getWindowSizeClass(width)) {
                WindowSize.COMPACT -> ExpandedLayout(1, 1)
                WindowSize.MEDIUM -> ExpandedLayout(4, 2)
                WindowSize.EXPANDED -> ExpandedLayout(6, 3)
                /*              WindowSize.COMPACT -> CompactLayout()
                                WindowSize.MEDIUM -> MediumLayout()
                                WindowSize.EXPANDED -> ExpandedLayout() */
            }
        }
    }
}