package com.example.indicadoresmvp.room

import androidx.room.Entity
import androidx.room.PrimaryKey

//Este es el dataclass de cada indicador, con sus items o columnas si fuera una BD:
@Entity(tableName = "indicadores")
data class Indicador(
    //    Ademas el id sera una primaryKey:
    @PrimaryKey(autoGenerate = true) val id: Int?,
    val id_indicador: String,
    val codigo: String,
    val nombre: String,
    val unidad_medida: String,
    val serie: List<Serie>,
    val imagenUrl: String
) {
    fun copy(id: String) {}

    //    La serie del indicador va en una data class aparte pero dentro del otro data class, porque es el mismo indicador:
    data class Serie(
        val valor: Double,
        val fecha: String
    )
}