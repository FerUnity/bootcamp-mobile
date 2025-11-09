package com.example.proyectopersonal.ui.screens.medlist

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.example.proyectopersonal.R
import com.example.proyectopersonal.ui.components.IndexTopBar
import kotlinx.coroutines.CoroutineScope

@Composable
fun MedListScreen(navController: NavController) {
//    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
//    //El snackBar necesita estos 2 val:
//    val scope: CoroutineScope = rememberCoroutineScope()
    Scaffold(
        topBar = {
          MedListTopBar()
        },
        floatingActionButton = {
           MedListFAB(navController)
        }

    ) { paddingValues ->
        MedListComponent(modifier = Modifier.padding(paddingValues), navController)

    }
}