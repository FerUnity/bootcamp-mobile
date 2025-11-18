package com.example.proyectopersonal.ui.screens.appMapaDesplegable

data class Hospital(var name: String, val lat: Double, val lon: Double)

val hospitales = listOf(
    Hospital("Nueva York", 40.7128, -74.0060), // Ejemplo de coordenadas
    Hospital("Los Ángeles", 34.0522, -118.2437),
    Hospital("Mutual", -33.45679824546918, -70.70086389231263),
    Hospital("San Borja", -33.46089576352979, -70.64177607116393),
    Hospital("J J Aguirre", -33.41995001119858, -70.65311040388185),
    Hospital("San Juan De Dios", -33.44193957960366, -70.6790216576723)

)
