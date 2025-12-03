package com.example.micalendariov4

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import com.example.micalendariov4.model.database.DatabaseDriverFactory
import com.example.micalendariov4.model.database.HolidayDatabase
import com.example.micalendariov4.view.App
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        val driver = DatabaseDriverFactory(this)
        val database = HolidayDatabase(driver)

        setContent {
            App(database)
        }
    }
}
