package com.example.indicadoresmvp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.indicadoresmvp.model.IndexViewModel
import com.example.indicadoresmvp.repository.IndicadorRepository
import com.example.indicadoresmvp.ui.components.Destination
import com.example.indicadoresmvp.ui.screens.indexDetailScreen.IndexDetailScreen
import com.example.indicadoresmvp.ui.screens.indexScreen.IndexScreen
import com.example.indicadoresmvp.ui.screens.indicadorlist.IndicadorListScreen
import com.example.indicadoresmvp.ui.screens.indicadornew.AddIndicadorScreen
import com.example.indicadoresmvp.ui.theme.IndicadoresMVPTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    companion object {
        lateinit var indexViewModel: IndexViewModel
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        // Splash Screen
        val splash = installSplashScreen()
        var keepSplash = true
        splash.setKeepOnScreenCondition { keepSplash }

        // Simula carga
        lifecycleScope.launch {
            delay(1000)
            keepSplash = false
        }
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
//        Cargar la lista de indicadores ya guardados, desde la BD:
//    Lo que hacemos aca, es que invocamos al IndexViewModel(context: Context),
//    desde el onCreate() del MainActivity,
//    de inmediato se ejecuta esta fun init, que invoca a la fun getIndicadores() del DAO,
//    la cual carga la lista de medicamentos desde la BD, y la almacena en la var _indicadores,
//    que es la lista de indicadores local, para tenerla disponible para la vista:

        indexViewModel = IndexViewModel(applicationContext)
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

        composable("indicador_Api_list") {
            IndicadorListScreen(navController)
        }
        composable("add_indicador") {
            AddIndicadorScreen(navController)
        }

    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AppNavigation()
}