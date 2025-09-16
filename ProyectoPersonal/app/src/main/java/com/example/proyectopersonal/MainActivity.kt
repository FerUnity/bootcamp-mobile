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
import com.example.proyectopersonal.model.AppDatabase
import com.example.proyectopersonal.model.UserSettingsViewModel
import com.example.proyectopersonal.room.MedRepository
import com.example.proyectopersonal.ui.screens.addMedicamentoScreen.AddMedicamentoScreen
import com.example.proyectopersonal.ui.screens.addMedicamentoScreen.AddMedicamentoViewModel
import com.example.proyectopersonal.ui.screens.medlist.MedListScreen
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class MainActivity : ComponentActivity() {
    companion object {
        lateinit var userSettingsViewModel: UserSettingsViewModel //Datastore
//        lateinit var addMedicamentoViewModel: AddMedicamentoViewModel //Para usar con BD
    }

    //Al abrir la app se muestra el splashScreen,
//    Se cargan los ajustes de usuario de la app,Theme y Lenguaje desde el DataStore,
//    Y se cargan la lista de medicamentos ya guardados,
//    y se muestra la pantalla principal:
    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        var showSplashScreen = true
        splashScreen.setKeepOnScreenCondition { showSplashScreen }

        lifecycleScope.launch {
            delay(2000)
            showSplashScreen = false
        }

        super.onCreate(savedInstanceState)

        //Cargar los ajustes guardados de usuario de la app,Theme y Lenguaje desde el DataStore:
        userSettingsViewModel = UserSettingsViewModel()
        userSettingsViewModel.getSettings(applicationContext)

//        Cargar la lista de medicamewntos ya guardados, desde la BD:
//    Lo que hacemos aca, es que invocamos al AddMedicamentoViewModel(context: Context),
//    desde el onCreate() del MainActivity,
//    de inmediato se ejecuta esta fun init, que invoca a la fun getMedicamentos() del DAO,
//    la cual carga la lista de medicamentos desde la BD, y la almacena en la var _medicamentos,
//    que es la lista de medicamentos local, para tenerla disponible para la vista:

        enableEdgeToEdge()
//                addMedicamentoViewModel = AddMedicamentoViewModel(
//                    MedRepository(
//                        AppDatabase.getDatabase(applicationContext).productDao()
//
//                ),
//                    applicationContext
//                )

        setContent {
            ProyectoPersonalTheme(userSettingsViewModel.theme) {
                AppNavigation()
            }
        }
    }

    //Y antes de cerrar la app, fun onDestroy(), se almacena los datos de conf en el DATASTORE,
    // y las BD en el json o SQLite:en JSON o SQLite, asi:
    override fun onDestroy() {
        super.onDestroy()
//        Guardamos los ajustes de usuario de la app,Theme y Lenguaje en el DataStore:
        userSettingsViewModel.saveSettings(applicationContext)

        //Guardamos la lista de medicamentos en el json:
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
        composable("add_med"){
            AddMedicamentoScreen(navController)
        }

    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AppNavigation()
}