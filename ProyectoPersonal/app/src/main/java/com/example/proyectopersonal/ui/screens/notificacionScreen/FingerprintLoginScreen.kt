package com.example.proyectopersonal.ui.screens.notificacionScreen

import android.widget.Toast
import androidx.biometric.BiometricPrompt
import androidx.compose.foundation.Image
import androidx.compose.foundation.content.MediaType.Companion.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import com.example.proyectopersonal.R
import java.util.concurrent.Executor

@Composable
fun FingerprintLoginScreen(canAuthenticate: Boolean, onAuthSuccess: () -> Unit) {
    val context = LocalContext.current
    val activity = context as FragmentActivity  // Cast context to FragmentActivity
    val executor: Executor = ContextCompat.getMainExecutor(context)

    val biometricPrompt = remember {
        BiometricPrompt(
            activity, executor,
            object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)
                    Toast.makeText(
                        context,
                        "Huella coincide, Login Exitoso",
                        Toast.LENGTH_SHORT
                    ).show()
                    onAuthSuccess()
                }

                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                    Toast.makeText(
                        context,
                        "Huella no coincidente, Login Fallido",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                    super.onAuthenticationError(errorCode, errString)
                    Toast.makeText(context, "Error: $errString", Toast.LENGTH_SHORT).show()
                }
            })
    }

    val promptInfo = remember {
        BiometricPrompt.PromptInfo.Builder()
            .setTitle("Autenticacion Biometrica")
            .setSubtitle("Use la Huella Digital para acceder a la app")
            .setNegativeButtonText("Cancelar")
            .build()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.baseline_fingerprint_24),
            contentDescription = "Fingerprint Icon",
            modifier = Modifier.size(100.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                if (canAuthenticate) {
                    biometricPrompt.authenticate(promptInfo)
                } else {
                    Toast.makeText(context, "Autenticacion Biometrica no disponible", Toast.LENGTH_LONG).show()
                }
            }
        ) {
            Text("LOGIN CON HUELLA DIGITAL")
        }
    }
}