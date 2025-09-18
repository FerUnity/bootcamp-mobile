package com.example.indicadoresmvp.model


//Este es el dataclass de cada indicador, con sus items o columnas si fuera una BD:
data class Indicador(
    val codigo: String,
    val nombre: String,
    val unidad_medida: String,
    val serie: List<Serie>
) {

//    La serie del indicador va en una data class aparte pero dentro del otro data class, porque es el mismo indicador:
    data class Serie(
        val valor: Double,
        val fecha: String
    )
}
