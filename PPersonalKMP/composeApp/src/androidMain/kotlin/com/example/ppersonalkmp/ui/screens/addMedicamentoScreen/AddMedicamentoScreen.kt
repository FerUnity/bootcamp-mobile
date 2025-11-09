package com.example.proyectopersonal.ui.screens.addMedicamentoScreen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import com.example.proyectopersonal.R
import com.example.proyectopersonal.ui.components.IndexTopBar
import kotlinx.coroutines.CoroutineScope

// Componente que muestra la pantalla del detalle del producto
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddMedicamentoScreen(navController: NavHostController) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    //El snackBar necesita estos 2 val:
    val scope: CoroutineScope = rememberCoroutineScope()
    val snackbarHostState: SnackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        topBar = {
            //Llamamos a la fun IndexTopBar() del archivo IndexTopBar.kt que arma el topBar:
            IndexTopBar(drawerState, scope, stringResource(R.string.app_name))
        },
        snackbarHost = {
            SnackbarHost(snackbarHostState)
        },
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        AddMedicamentoForm(
            modifier = Modifier.padding(innerPadding),
            navController,
            snackbarHostState,
            scope
        )
    }
}