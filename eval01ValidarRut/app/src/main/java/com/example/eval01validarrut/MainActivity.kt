package com.example.eval01validarrut

import android.R.attr.label
import android.icu.text.CaseMap
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.eval01validarrut.ui.theme.Eval01ValidarRutTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.w3c.dom.Text
import kotlin.math.abs

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Eval01ValidarRutTheme {
                calculo()

            }
        }
    }
}

@Composable
fun NavigationMenu(drawerState: DrawerState, scope: CoroutineScope) {
    Column(
        Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(Color.LightGray)
    ) {
        Text(
            text = "Menú",
            modifier = Modifier.padding(bottom = 12.dp)
                .clickable{scope.launch { drawerState.close() }}
        )
        HorizontalDivider()

        //Con el sgte btn puedo cerrar el ModalNavigationDrawer, ver modifier:
        Text(
            text = "Opción 1",
            //cerrar el ModalNavigationDrawer
            modifier = Modifier.clickable {/*TO DO()*/ }
        )

        //Este btn no hace nada
        Text(
            text = "Opción 2",
            modifier = Modifier.clickable { /*TO DO()*/ }
        )

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(drawerState: DrawerState, scope: CoroutineScope) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = "Verificador de Rut",
                textDecoration = TextDecoration.None,
                fontSize = 30.sp

            )

        },
        navigationIcon = {
            Icon(
                //imageVector = Icons.Filled.ArrowBack,
                //tint = Color(white),
                imageVector = Icons.Filled.Menu,
                contentDescription = "Inicio",
                //Abrir el ModalNavigationDrawer
                modifier = Modifier.clickable { scope.launch { drawerState.open() } }

            )
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Blue,
            titleContentColor = Color.White,
            navigationIconContentColor = Color.Black
        )

    )
}

@Composable
fun calculo(modifier: Modifier = Modifier) {
    var numero1 by remember { mutableStateOf("") }
    var numero2 by remember { mutableStateOf("") }
    var resultado by remember { mutableIntStateOf(0) }
    var textoFinal by remember { mutableStateOf("") }
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {

            //Fun de los textos del ModalNavigationDrawer
            NavigationMenu(drawerState, scope)



        }
    )

    //Abre content de ModalNavigationDrawer()
    {

        Scaffold(
            topBar = {

                TopBar(drawerState, scope)


            }
        )
        //Fin atributos Scaffold
        {
            //Cuerpo Scaffold:

//            innerPadding ->
//            calculo(
//                modifier = Modifier.padding(innerPadding)
//            )

        } //Cierre content Scaffold

    } //Cierre ModalNavDrawer


    Column(
        modifier = Modifier.fillMaxSize(),
        //Central horizontal y verticalmente
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center


    ) {
        textoEntrada(
            "Ingrese rut sin puntos ni guion y sin digito verificador",
            numero1,
            { numero1 = it })

        textoEntrada(
            "Ingrese digito verificador",
            numero2,
            { numero2 = it })

        Button(onClick = {
            //Invertimos el numero1 que es el rut sin digito verif
            var numero1ComoCadena = numero1.toString()
            //Invertimos el numero1
            var cadenaInvertida = numero1ComoCadena.reversed()
            //Desomponemos la cadenaInvertida en digitos:
            //Creamos la lista digitos de enteros:
            var digitos = mutableListOf<Int>()
            for (caracter in cadenaInvertida) {
                //Los convertimos en int antes de agregarlos a la lista digitos
                digitos.add(caracter.toString().toInt())
            }
            //Recorremos la lista digitos en orden:
            var digito: Int
            var contador = 2
            var producto: Int
            var sumaProd = 0
            for (i in digitos.indices) {
                digito = digitos[i]
                if (contador <= 7) {
                    producto = digito * contador
                    contador++
                } else {
                    contador = 2
                    producto = digito * contador
                    contador++
                }

                sumaProd += producto
            }
            //Obtener la parte entera de la division asi:
            var Division = sumaProd / 11

            var Multipl = Division * 11

            var Resta = abs(Multipl - sumaProd)

            resultado = 11 - Resta

            if (resultado == numero2.toInt()){
                textoFinal = "El valor ingresasdo SI corresponde a un Rut real"
            }
            else {
                textoFinal = "El valor ingresasdo NO corresponde a un Rut real"
            }
            //Borrar texto de los TextField:
            numero1 = ""
            numero2 = ""


        })
        {
            //Cuerpo button:
            Icon(
                //Icono +:
                imageVector = Icons.Filled.CheckCircle,
                contentDescription = "Calcular",
                modifier = Modifier.padding(end = 4.dp)
            )
            //Texto boton:
            Text(
                "Verificar",
                fontSize = 20.sp,
                modifier = Modifier.padding(start = 8.dp))


        }
        //Agregamos un separador horizontal
        HorizontalDivider(
            modifier = Modifier.padding(
                horizontal = 16.dp,
                vertical = 24.dp
            )
        )

        Text(text = textoFinal)

    }

}

@Composable
fun textoEntrada(
    //Parametros
    title: String,
    text: String,
    onValueChange: (String) -> Unit
) {
    //Compopnenetes Widget:
    TextField(
        value = text,
        onValueChange = onValueChange,
        label = {
            Text(text = title)

        }
    )
   //Agregamos un separador horizontal
            HorizontalDivider(
                modifier = Modifier.padding(
                    horizontal = 16.dp,
                    vertical = 24.dp
                )
            )



}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Eval01ValidarRutTheme {
        calculo()
    }
}