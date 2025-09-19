package com.example.indicadoresmvp.ui.screens.indicadornew

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController

@Composable
fun AddIndicadorScreen(navController: NavController) {
    Scaffold(
        topBar = {
            AddIndicadorTopBar(navController)
        }
    ) { padding ->
        AddIndicadorForm(modifier = Modifier.padding(padding), navController)
    }
}