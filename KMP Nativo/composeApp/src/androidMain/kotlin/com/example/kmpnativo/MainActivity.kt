package com.example.kmpnativo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.kmpnativo.data.DriverFactory
import com.example.kmpnativo.data.UserRepository
import com.example.kmpnativo.model.AndroidBatteryLevel
import com.example.kmpnativo.ui.App

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
//        Aca  calculamos el BATT level cuya clase AndroidBatteryLevel esta el platforn de android:
//        val batteryLevel = AndroidBatteryLevel(applicationContext)
        val repo = UserRepository(DriverFactory(applicationContext))


        setContent {
//            Luego ponemos como param en el composable App() el BATT level, para mostrarlo en ese UI:
            App(repo)
        }
    }
}

 /*   @Preview
    @Composable
    fun AppAndroidPreview() {
        App(0)
    }
*/

