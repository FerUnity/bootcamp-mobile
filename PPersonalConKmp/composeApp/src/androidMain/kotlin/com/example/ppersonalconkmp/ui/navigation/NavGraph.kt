package com.example.proyectopersonal.ui.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.proyectopersonal.ui.screens.IndexDetailScreen.IndexDetailScreen
import com.example.proyectopersonal.ui.screens.IndexScreen.ConsultasScreen
import com.example.proyectopersonal.ui.screens.IndexScreen.IndexScreen
import com.example.proyectopersonal.ui.screens.addMedicamentoScreen.AddMedicamentoScreen
import com.example.proyectopersonal.ui.screens.appMapaDesplegable.AppConMapaScreen
import com.example.proyectopersonal.ui.screens.appMapaDesplegable.AppMapaListaConMVVM
import com.example.proyectopersonal.ui.screens.audiorecorder.AudioScreen
import com.example.proyectopersonal.ui.screens.cameraScreen.CameraScreen
import com.example.proyectopersonal.ui.screens.home.HomeScreen
import com.example.proyectopersonal.ui.screens.home.RtdbScreen
import com.example.proyectopersonal.ui.screens.initialscreen.InitialScreen
import com.example.proyectopersonal.ui.screens.login.LoginScreen
import com.example.proyectopersonal.ui.screens.medlist.MedListScreen
import com.example.proyectopersonal.ui.screens.notificacionScreen.ExamplesScreen
import com.example.proyectopersonal.ui.screens.sensorScreen.SensorView
import com.example.proyectopersonal.ui.screens.settings.SettingsScreen
import com.example.proyectopersonal.ui.screens.signup.SignUpScreen
import com.example.proyectopersonal.ui.theme.ThemeOption
import com.google.firebase.auth.FirebaseAuth

// Componente que permite navegar entre pantallas
@Composable
fun AppNavigation(
    auth: FirebaseAuth,
    navController: NavHostController,
    skipLogin: Boolean = true,
    onChangeTheme: (ThemeOption) -> Unit = {},
    themeOpt: ThemeOption
) {
//    val navController: NavHostController = rememberNavController()
//    NavHost(navController = navController, startDestination = "home") {
    NavHost(navController = navController, startDestination = "initial") {
        composable("initial") {
            InitialScreen(
                navigateToLogin = { navController.navigate("logIn") },
                navigateToSignUp = { navController.navigate("signUp") },
                navigateToHome = { navController.navigate("home") }
            )
        }
        composable("logIn") {
            LoginScreen(
                auth,
                navController,
                navigateToHome = { navController.navigate("home") }
            )
        }
        composable("signUp") {
            SignUpScreen(auth, navController)
        }

        composable("home") {
            HomeScreen(navcontroller = navController)
        }

        composable("rtdbScreen") {
            RtdbScreen(navController = navController)
        }


        composable("index") {
            IndexScreen(navController)
        }

        composable("consultas") {
            ConsultasScreen(
                navController = navController,
                innerPadding = PaddingValues()
            )
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

//        Se llama de boton en indexForm
        composable("notificacion") {
            ExamplesScreen(
                navController = navController
            )
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

        composable("sensorView") {
            SensorView(
                navController,
                PaddingValues()
            )
        }

        composable("audioScreen") {
            AudioScreen(
                navController,
                PaddingValues()
            )
        }

    }
}