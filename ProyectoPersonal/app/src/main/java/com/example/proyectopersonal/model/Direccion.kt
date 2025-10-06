package com.example.proyectopersonal.model

data class Direccion(var name: String, var address: String)

val direcciones = listOf(
    Direccion("Mutual", "Av. Alameda 4848, Estación Central, Región Metropolitana"),
    Direccion("San Borja", "Av. Sta. Rosa 1234, Santiago, Región Metropolitana"),
    Direccion("J J Aguirre", "Dr. Carlos Lorca Tobar 999, Independencia, Región Metropolitana"),
    Direccion("San Juan De Dios", "Huerfanos 3255, Santiago, Región Metropolitana")

)
