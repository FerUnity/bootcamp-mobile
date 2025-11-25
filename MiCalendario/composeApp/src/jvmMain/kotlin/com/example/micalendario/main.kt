package com.example.micalendario

import androidx.compose.runtime.remember
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import com.example.micalendario.calendar.CalendarViewModel
import com.example.micalendario.network.HolidayApi
import com.example.micalendario.network.httpClient
import com.example.micalendario.sqldelight.DatabaseDriverFactory
import com.example.micalendario.sqldelight.HolidayRepository

fun main() = application {
    val windowState = rememberWindowState(width = 1000.dp, height = 800.dp)
    Window(
        onCloseRequest = ::exitApplication,
        state = windowState,
        title = "micalendario",
    ) {
        val viewModel = remember {
            val driverFactory = DatabaseDriverFactory()
            val db = AppDatabaseExample(driverFactory.createDriver())
            val repo = HolidayRepository(database = db, api = HolidayApi(httpClient))
            CalendarViewModel(repo)
        }

        DesktopCalendarRoot(
            viewModel = viewModel,
            windowWidth = windowState.size.width
        )
    }
}