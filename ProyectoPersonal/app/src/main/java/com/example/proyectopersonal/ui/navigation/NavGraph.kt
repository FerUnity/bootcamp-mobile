package com.example.proyectopersonal.ui.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.proyectopersonal.ui.screens.IndexDetailScreen.IndexDetailScreen
import com.example.proyectopersonal.ui.screens.IndexScreen.IndexScreen
import com.example.proyectopersonal.ui.screens.addMedicamentoScreen.AddMedicamentoScreen
import com.example.proyectopersonal.ui.screens.appMapaDesplegable.AppConMapaScreen
import com.example.proyectopersonal.ui.screens.appMapaDesplegable.AppMapaListaConMVVM
import com.example.proyectopersonal.ui.screens.audiorecorder.AudioScreen
import com.example.proyectopersonal.ui.screens.cameraScreen.CameraScreen
import com.example.proyectopersonal.ui.screens.medlist.MedListScreen
import com.example.proyectopersonal.ui.screens.sensorScreen.SensorView
import com.example.proyectopersonal.ui.screens.settings.SettingsScreen
import com.example.proyectopersonal.ui.theme.ThemeOption

// Componente que permite navegar entre pantallas
@Composable
fun AppNavigation(
    skipLogin: Boolean = true,
    onChangeTheme: (ThemeOption) -> Unit = {},
    themeOpt: ThemeOption
) {
    val navController: NavHostController = rememberNavController()
    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            IndexScreen(navController)
        }
        composable("med_list") {
            MedListScreen(navController)
        }

        composable("index_detail/{index}") { backStackEntry ->
            //val id = backStackEntry.arguments?.getString("id")
            val index = backStackEntry.arguments?.getString("index")
            //val date = backStackEntry.arguments?.getString("date")
            IndexDetailScreen(navController, index)
        }

        //Aca otro composable para formulario de medicamentos seleccionado:
        composable("add_med") {
            AddMedicamentoScreen(navController)
        }

        composable("map") {
            AppConMapaScreen(
                navController,
                PaddingValues()
            )

        }

        composable("settings") {
            SettingsScreen(
                themeOpt = themeOpt,
                onChangeTheme = onChangeTheme,
                onBack = { navController.popBackStack() }

            )
        }

        composable("map_search") {
            AppMapaListaConMVVM(
                innerPadding = PaddingValues()
            )

        }

        composable("camera") {
            CameraScreen(
                navController,
                PaddingValues()
            )

        }

        composable("sensorView"){
            SensorView()
        }

        composable("audioScreen"){
            AudioScreen(
                navController,
                PaddingValues()
            )
        }

    }
}