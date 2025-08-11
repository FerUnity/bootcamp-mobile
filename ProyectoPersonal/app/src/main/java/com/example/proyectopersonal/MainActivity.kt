package com.example.proyectopersonal

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.proyectopersonal.ui.theme.ProyectoPersonalTheme
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.proyectopersonal.ui.screens.IndexDetailScreen.IndexDetailScreen
import com.example.proyectopersonal.ui.screens.IndexScreen.IndexScreen
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavHostController
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        var showSplashScreen = true
        splashScreen.setKeepOnScreenCondition { showSplashScreen }

        lifecycleScope.launch {
            delay(2000)
            showSplashScreen = false
        }

        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            ProyectoPersonalTheme {
                AppNavigation()
            }
        }
    }
}

// Componente que permite navegar entre pantallas
@Composable
fun AppNavigation() {
    val navController: NavHostController = rememberNavController()
    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            IndexScreen(navController)
        }
        composable("index_detail/{index}") { backStackEntry ->
            //val id = backStackEntry.arguments?.getString("id")
            val index = backStackEntry.arguments?.getString("index")
            //val date = backStackEntry.arguments?.getString("date")
            IndexDetailScreen(navController, index)
        }

        //Faltaria otro composable por ej detalle del medicamento seleccionado:

    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ProyectoPersonalTheme {
        AppNavigation()
    }
}