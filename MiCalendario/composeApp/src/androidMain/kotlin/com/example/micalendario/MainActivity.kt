package com.example.micalendario

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        initKoin() //fun initial de Koin
        setContent {
            App(
                onBackPressed = { finish() }
            )
        }
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    App()
}