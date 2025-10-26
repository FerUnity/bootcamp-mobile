package com.example.proyectopersonal.ui.screens.login

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.proyectopersonal.R
import com.example.proyectopersonal.ui.theme.Black
import com.example.proyectopersonal.ui.theme.Gray
import com.example.proyectopersonal.ui.theme.SelectedField
import com.example.proyectopersonal.ui.theme.UnselectedField
import com.google.firebase.auth.FirebaseAuth

//Pantalla de inicio de sesion una vez que el user ya se ha registrado, en la signUpScreen:
@Composable
fun LoginScreen(
    auth: FirebaseAuth,
    navController: NavController,
    navigateToHome: () -> Unit
)
{
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
//            .background(Black)
            .background(Brush.verticalGradient(listOf(Color.Black,Color.Gray), startY = 0f, endY = 900f))
            .padding(horizontal = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Row() {
            Icon(
                painter = painterResource(id = R.drawable.ic_back_24),
                contentDescription = "",
                tint = White,
                modifier = Modifier
                    .padding(vertical = 24.dp)
                    .size(24.dp)
                    .clickable {
                        navController.popBackStack()
                    }
            )
            Spacer(modifier = Modifier.weight(1f))
        }

        Text("Email", color = White, fontWeight = FontWeight.Bold, fontSize = 40.sp)
        TextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = UnselectedField,
                focusedContainerColor = SelectedField
            )
        )
        Spacer(Modifier.height(48.dp))
        Text("Password", color = White, fontWeight = FontWeight.Bold, fontSize = 40.sp)
        TextField(
            value = password, onValueChange = { password = it },
            label = { Text("Password") },
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = UnselectedField,
                focusedContainerColor = SelectedField
            )
        )
        Spacer(Modifier.height(48.dp))

//        Boton Log in que verificara us y pw desde el Firebase registrado.
//        Para eso usara el param auth
        Button(onClick = {
            auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    //Si la tarea de login fue exitosa: navegamos al Home
                    // con la fun lmbda navigateToHome(),
                    // sino mensaje de error:
                    navigateToHome()
                    Log.i("aris", "LOGIN OK")
                } else {
                    //Error
                    /*        Toast.makeText(
                                navController,
                                "Error en el login", Toast.LENGTH_LONG).show()*/
                    Log.i("aris", "LOGIN KO")
                }
            }
        }) {
            Text(text = "Login")
        }

        Spacer(Modifier.height(48.dp))

//        Imagen hospital:
        Image(
            painter = painterResource(id = R.mipmap.ic_bg_hospitales_foreground),
            contentDescription = "Hospital",
            contentScale = ContentScale.Fit,
            //Se tiene definir el tamaño asi:
            modifier = Modifier
                .fillMaxSize()
//            Tecortar la img circularmente para que no se vea el borde cuadrado
//            modifier = Modifier.clip(CircleShape)
        )
    }
}