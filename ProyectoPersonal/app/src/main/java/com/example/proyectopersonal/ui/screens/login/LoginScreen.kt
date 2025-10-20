package com.example.proyectopersonal.ui.screens.login

import android.util.Log
import androidx.compose.foundation.background
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
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.proyectopersonal.R
import com.example.proyectopersonal.ui.theme.Black
import com.example.proyectopersonal.ui.theme.SelectedField
import com.example.proyectopersonal.ui.theme.UnselectedField
import com.google.firebase.auth.FirebaseAuth

//Pantalla de inicio de sesion una vez que el user ya se ha registrado, en la signUpScreen:
@Composable
fun LoginScreen(auth: FirebaseAuth, navigateToHome:() -> Unit) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Black)
            .padding(horizontal = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Row(){
            Icon(
                painter = painterResource(id = R.drawable.ic_back_24),
                contentDescription = "",
                tint = White,
                modifier = Modifier
                    .padding(vertical = 24.dp)
                    .size(24.dp)
            )
            Spacer(modifier = Modifier.weight(1f))
        }

        Text("Email", color = White, fontWeight = FontWeight.Bold, fontSize = 40.sp)
        TextField(
            value = email,
            onValueChange = { email = it },
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
            auth.signInWithEmailAndPassword(email, password).addOnCompleteListener{ task ->
                if(task.isSuccessful){
                    //Si la tarea de login fue exitosa: navegamos al Home
                    // con la fun lmbda navigateToHome(),
                    // sino mensaje de error:
                    navigateToHome()
//                    navController.navigate("home")
                    Log.i("aris", "LOGIN OK")
                }else{
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
    }
}