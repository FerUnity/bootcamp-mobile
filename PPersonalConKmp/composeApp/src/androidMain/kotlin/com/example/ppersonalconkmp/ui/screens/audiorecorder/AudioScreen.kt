package com.example.proyectopersonal.ui.screens.audiorecorder

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.DrawerValue
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

@Composable
fun AudioScreen(
    navController: NavHostController,
    innerPadding: PaddingValues
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    //El snackBar necesita estos 2 val:
    val scope: CoroutineScope = rememberCoroutineScope()
    val snackbarHostState: SnackbarHostState = remember {
        SnackbarHostState()
    }

    Scaffold(
        //Que el scaffold tenga un topBar
        topBar = {
            //Llamamos a la fun IndexTopBar() del archivo IndexTopBar.kt que arma el topBar:
            IndexTopBar(
                navController, drawerState, scope, stringResource(R.string.app_name)
            )
        },
        snackbarHost = {
            SnackbarHost(snackbarHostState)
        },

        //Que el scaffold ocupe toda la pantalla
        modifier = Modifier.fillMaxSize()
        //Conten del Scaffold:
    )
    { innerPadding ->
        AudioRecorderScreen(
            innerPadding = innerPadding
        )




    }   //Cierre cont Scaffold()


}
//Cierre fun AudioScreen()
