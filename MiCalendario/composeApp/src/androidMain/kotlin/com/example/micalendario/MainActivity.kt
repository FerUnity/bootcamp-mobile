package com.example.micalendario

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.MaterialTheme
import com.example.micalendario.calendar.CalendarScreen
import com.example.micalendario.calendar.CalendarViewModel
import com.example.micalendario.network.HolidayApi
import com.example.micalendario.network.httpClient
import com.example.micalendario.sqldelight.Database
import com.example.micalendario.sqldelight.DatabaseDriverFactory
import com.example.micalendario.sqldelight.HolidayRepository

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        initKoin() //fun initial de Koin
        val driverFactory = DatabaseDriverFactory(this)
        val database = Database(driverFactory)
        val api = HolidayApi(httpClient)
        val repo = HolidayRepository(database, api)
        val viewModel = CalendarViewModel(repo)
        setContent {
            MaterialTheme {
                CalendarScreen(viewModel = viewModel)
//            App()
            }
        }
    }
}
