package com.example.proyectopersonal.room

import com.example.proyectopersonal.model.ProductDAO
import com.example.proyectopersonal.model.ProductData
import com.example.ppersonalconkmp.services.MedApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

//Es un cjto o piscina de datos que puedo administrar.
// Actua como mediador, unificando AL MISMO TIEMPO,
// el acceso a APIs remotas como Firebase desde el MedApiService.kt
// y tambien a la BD local ROOM desde el ProductDAO.kt:
// En este caso dministraremos este repositorio para llegar a la BD local ROOM desde el ProductDAO:
class MedRepository(private val dao: ProductDAO, private val apiService: MedApiService) {
    val medicamentos: Flow<List<ProductData>> = dao.getAll()

    // Funciones que solo interactúan con el ROOM local
    suspend fun addMedicamento(medicamento: ProductData) {
        dao.insertMedicamento(medicamento)
    }

    suspend fun removeMed(medicamento: ProductData) {
        dao.removeMedicamento(medicamento)
    }

    suspend fun updateMed(medicamento: ProductData) {
        dao.updateMedicamento(medicamento)

    }
    // Funciones que solo interactúan con la API Service externa FireBase
//    Esta fun fetchMeds, recibe todos los medicamentod pero desde la Nube (FireBase)
    fun fetchMeds(): Flow<List<ProductData>> = flow {
        val medicamentos = apiService.getMedicamentos()
        //Con emit se liberan los datos:
        emit(medicamentos)
    }

//    fun para enviar un medicamento a la nube que se obtienen de recorrer la lista de medicamentos local:
    fun pushMed(medicamento: ProductData): Flow<ProductData> = flow {
//        Subimos el med a la nube con apiService.addMedicamento():
        val newMedicamento = apiService.addMedicamento(medicamento)
        emit(newMedicamento)
    }

//    fun para enviar varios medicamentos a la nube, que se obtienen de recorrer la lista de medicamentos:
    fun pushMeds(medicamentos: List<ProductData>): Flow<List<ProductData>> = flow {
        for (medicamento in medicamentos) {
//            Subimos los med a la nube con apiService.addMedicamento():
            apiService.addMedicamento(medicamento)
        }
        emit(medicamentos)
    }




    }


