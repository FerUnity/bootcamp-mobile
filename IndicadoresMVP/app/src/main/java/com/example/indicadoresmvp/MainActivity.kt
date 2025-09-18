package com.example.indicadoresmvp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.indicadoresmvp.ui.components.Destination
import com.example.indicadoresmvp.ui.screens.indexDetailScreen.IndexDetailScreen
import com.example.indicadoresmvp.ui.screens.indexScreen.IndexScreen
import com.example.indicadoresmvp.ui.theme.IndicadoresMVPTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            IndicadoresMVPTheme {
                AppNavigation()
            }
        }
    }
}

// Componente que permite navegar entre pantallas
@Composable
fun AppNavigation() {
    val navController: NavHostController = rememberNavController()
    NavHost(navController = navController, startDestination = "nacional") {
        composable("nacional") {
            IndexScreen(navController, Destination.NAC)
        }


        composable("internacional") {
            IndexScreen(navController, Destination.INT)
        }

    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AppNavigation()
}