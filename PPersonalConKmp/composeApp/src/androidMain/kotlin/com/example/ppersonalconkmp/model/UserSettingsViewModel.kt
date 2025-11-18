package com.example.proyectopersonal.model

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

//
//Esta es una clase que hereda de la clase ViewModel, no un componenete a mostrar graficamente,
// Esta clase es solo para guardar datos de la config propia de la app:
// El: El Theme y el lenguaje,
// usando sharedPreferences, DatStore, Datos locales, etc:

//Que herede de ViewModel:
class UserSettingsViewModel: ViewModel() {
    var theme: String by mutableStateOf("system") //"system" es el valor por def de theme.
    var language: String by mutableStateOf("es-cl") //español-chile valor por def de languaje.

//    Creamos una instancia para los syharedPreferences:
//    val userPreferences: SharedPreferences = getSharedPreferences("user_preferences", Context.MODE_PRIVATE)
//Como no estamos en el contexto de la activifdad no se puede ibnvocar al getSharedPreferences, asi que usamos:

    //Entonces fijamos ambos valores con una fun setTheme() y setLanguaje():
//    fun setTheme(theme: String) {
//        this.theme = theme
////        Una vez fijado el tema lo guardamos en el sharedPreferences (userPreferences),
////        a traves de un valor llamado "edit":
//        val editor = userPreferences.edit()
//        //Guardamos el tema(valor) theme con la clave "theme":
//        editor.putString("theme", theme) //(clave, valor)
//        editor.apply()
//    }
//
//    fun setLanguaje(languaje: String) {
//        this.languaje = languaje
////        Idem que con el tema:
//        val editor = userPreferences.edit()
//        editor.putString("languaje", languaje)
//        editor.apply()
//
//    }


    //  DATASTORE:
//  Veremos ahora el caso de usar DATASTORE para guardar datos de conf de theme y lenguaje:
    //Cramos 2 ctes o val que rep los campos del Data store(en este caso solo 2: theme y languaje)
    val THEME_KEY = stringPreferencesKey("theme")
    val LANGUAGE_KEY = stringPreferencesKey("language")

    // y una tercera cte o val que rep el mismo Datastore:
    val Context.dataStore by preferencesDataStore("userSettings")

    //    Y creamos 2 fun,
    //    una para leer los datos de configuracion del Datastore,
    //    que se invoca al abrir la app: onCreate() del MainActivity:
    fun getSettings(context: Context) {
        runBlocking {
            val dataStore = context.dataStore
            val preferences = dataStore.data.first()
            if (preferences[THEME_KEY] != null){
                theme = preferences[THEME_KEY].toString()
            }
            if (preferences[LANGUAGE_KEY] != null){
                language = preferences[LANGUAGE_KEY].toString()
            }


        }
    }

    //    y otra fun para guardar los datos en el DataStore,
    //    que se invoca al cerrar la app: onDestroy() del MainActivity:
    fun saveSettings(context: Context) {
        runBlocking {
            val dataStore = context.dataStore
            dataStore.edit { preferences ->
                preferences[THEME_KEY] = theme
                preferences[LANGUAGE_KEY] = language
            }
        }
    }
    fun saveThemeSetting(context: Context) {
        runBlocking {
            val dataStore = context.dataStore
            dataStore.edit { preferences ->
                preferences[THEME_KEY] = theme
            }
        }
    }


    // Pero esto se lee y se guarda una vez en el uso de la app que es al comienzo,
// por eso para leer los datos de conf(getSettings()) hay que ir a MainActivity al momento de iniciae la app: en el onCreate:

    //Y al final antes de destruir la app se guardan los datos de conf(saveSettings()) en el DataStore: durante el onDestroy

    //    val Context.dataStore by preferencesDataStore("userSettings")

}