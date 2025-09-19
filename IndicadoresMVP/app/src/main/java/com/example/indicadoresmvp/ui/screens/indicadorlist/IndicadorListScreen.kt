package com.example.indicadoresmvp.ui.screens.indicadorlist

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController

@Composable
fun IndicadorListScreen(navController: NavController) {
    Scaffold(
        topBar = {
            IndicadorListTopBar()
        },
        floatingActionButton = {
            IndicadorListFAB(navController)
        }
    ) { padding ->
        IndicadorListComponent(modifier = Modifier.padding(padding), navController)
    }
}