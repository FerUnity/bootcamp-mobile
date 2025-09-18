package com.example.indicadoresmvp.service

import com.example.indicadoresmvp.model.Indicador
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

//Es un cjto o piscina de datos que puedo administrar.
// Actua como mediador, unificando AL MISMO TIEMPO,
// el acceso a APIs remotas como Firebase desde el IndicadoresApiService.kt
// y tambien a la BD local ROOM desde el DAO si hubiera:

class IndicadoresRepository(private val apiService: IndicadoresApiService) {
    suspend fun obtenerIndicadorFecha(indicador: String, fecha: String): Flow<Indicador> = flow {
        val indicador = apiService.obtenerIndicadorPorFecha(indicador, fecha)
        emit(indicador)
    }


}