package com.example.proyectopersonal.ui.screens.cameraScreen

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
import com.example.proyectopersonal.ui.screens.appMapaDesplegable.AppMapaHospitalesMVVM
import kotlinx.coroutines.CoroutineScope

@Composable
fun CameraScreen(
    navController: NavHostController,
    innerPadding: PaddingValues
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    //El snackBar necesita estos 2 val:
    val scope: CoroutineScope = rememberCoroutineScope()
    val snackbarHostState: SnackbarHostState = remember {
        SnackbarHostState()
    }
    //Scaffold es un componente que permite construir pantallas organizadas,
    // incorporando áreas predefinidas como:
    //topBar: barra superior
    //bottomBar: barra inferior
    //floatingActionButton(FAB): botón flotante
    //content: zona principal de la pantalla
    //Es ideal para crear pantallas coherentes con las guías de Material Design.

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
        //Llamamos a la fun FAButton() del archivo FAButton.kt que arma el FAB:
        // Boton flotante redondo, rojo con el singo +:
//        floatingActionButton = {
//            FAButton()
//        },

        //Que el scaffold ocupe toda la pantalla
        modifier = Modifier.fillMaxSize()
        //Conten del Scaffold:
    )
    {
            innerPadding ->
        CameraFunction(
            innerPadding = innerPadding
        )



    }   //Cierre cont Scaffold()


}
//Cierre fun AppConMapaScreen()