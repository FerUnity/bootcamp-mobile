package com.example.proyectopersonal.ui.screens.IndexScreen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.example.proyectopersonal.ui.components.FAButton
import com.example.proyectopersonal.ui.components.IndexTopBar

@Composable
fun IndexScreen(navController: NavHostController) {
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
            IndexTopBar()
        },
        //Llamamos a la fun FAButton() del archivo FAButton.kt que arma el FAB:
        // Boton flotante redondo, rojo con el singo +:
        floatingActionButton = {
            FAButton()
        },

        //Que el scaffold ocupe toda la pantalla
        modifier = Modifier.fillMaxSize()
    )
    { //Conten del Scaffold:
            innerPadding ->
        //Llamamos a la fun IndexForm() del archivo IndexForm.kt que arma el formulario:
        IndexForm(navController, innerPadding)

    }   //Cierre cont Scaffold()


}
//Cierre fun IndexScreen()




