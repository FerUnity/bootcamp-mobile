package com.example.indicadoresmvp.repository

import com.example.indicadoresmvp.room.Indicador
import com.example.indicadoresmvp.room.IndicadorDAO
import com.example.indicadoresmvp.service.IndicadoresApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

//Es un cjto o piscina de datos que puedo administrar.
// Actua como mediador, unificando AL MISMO TIEMPO,
// el acceso a APIs remotas como Firebase desde el IndicadoresApiService.kt
// y tambien a la BD local ROOM desde el IndicadorDAO.kt:
// En este caso dministraremos este repositorio para llegar a la BD local ROOM desde el ProductDAO

class IndicadorRepository(private val dao: IndicadorDAO, private val apiService: IndicadoresApiService) {
    val indicadores: Flow<List<Indicador>> = dao.getAll()

    // Funciones que solo interactúan con el ROOM local
    suspend fun addIndicador(indicador: Indicador) {
        dao.insertIndicador(indicador)
    }

    suspend fun removeMed(indicador: Indicador) {
        dao.removeIndicador(indicador)
    }

    suspend fun updateMed(indicador: Indicador) {
        dao.updateIndicador(indicador)

    }
    // Funciones que solo interactúan con la API Service externa FireBase
//    Esta fun fetchMeds, recibe todos los medicamentod pero desde la Nube (FireBase)
    fun fetchIndicadores(): Flow<List<Indicador>> = flow {
        val indicadores = apiService.obtenerIndicadores()
        //Con emit se liberan los datos:
        emit(indicadores)
    }

    //    fun para enviar un medicamento a la nube que se obtienen de recorrer la lista de medicamentos local:
    fun pushIndicador(indicador: Indicador): Flow<Indicador> = flow {
//        Subimos el med a la nube con apiService.addMedicamento():
        val newIndicador = apiService.addIndicador(indicador)
        emit(newIndicador)
    }

    //    fun para enviar varios medicamentos a la nube, que se obtienen de recorrer la lista de medicamentos LOCAL:
    fun pushIndicadores(indicadores: List<Indicador>): Flow<List<Indicador>> = flow {
        for (indicador in indicadores) {
//            Subimos los med a la nube con apiService.addIndicador():
            apiService.addIndicador(indicador)
        }
        emit(indicadores)
    }




}