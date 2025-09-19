package com.example.indicadoresmvp.ui.screens.indexScreen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.example.indicadoresmvp.ui.components.Destination
import com.example.indicadoresmvp.ui.components.IndexTopBar
import com.example.indicadoresmvp.ui.components.SectionBottomBar

@Composable
fun IndexScreen(navController: NavHostController, destination: Destination) {
    val snackbarHostState = remember { SnackbarHostState() }
    Scaffold(
        //Que el scaffold tenga un topBar
        topBar = {
            //Llamamos a la fun IndexTopBar() del archivo IndexTopBar.kt que arma el topBar:
            IndexTopBar()
        },
        snackbarHost = {
            //Llamamos a la fun SnackbarHost() del archivo SnackbarHost.kt que arma el snackbar:
            SnackbarHost(snackbarHostState)
        },
        bottomBar = {
            //Llamamos a la fun SectionBottomBar() del archivo SectionBottomBar.kt que arma el bottomBar:
            SectionBottomBar(navController, destination)
        },
        //Que el scaffold ocupe toda la pantalla
        modifier = Modifier.fillMaxSize()
    )
    { //Conten del Scaffold:
        innerPadding ->
        //Llamamos a la fun IndexForm() del archivo IndexForm.kt que arma el formulario:
        IndexForm(
            navController,
            snackbarHostState,
            innerPadding,
            destination
        )


    }   //Cierre cont Scaffold()


}
//Cierre fun IndexScreen()


