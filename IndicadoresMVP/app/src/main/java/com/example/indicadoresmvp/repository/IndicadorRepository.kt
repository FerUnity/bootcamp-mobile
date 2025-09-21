package com.example.indicadoresmvp.repository

import com.example.indicadoresmvp.room.Indicador
import com.example.indicadoresmvp.room.IndicadorDAO
import com.example.indicadoresmvp.service.IndicadoresApiService
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

//Es un cjto o piscina de datos que puedo administrar.
// Se encarga bde traspasasr los datos desde la API a la BD local ROOM.
// Actua como mediador, unificando AL MISMO TIEMPO,
// el acceso a APIs remotas como Firebase desde el IndicadoresApiService.kt
// y tambien a la BD local ROOM desde el IndicadorDAO.kt:
// En este caso dministraremos este repositorio para llegar a la BD local ROOM desde el ProductDAO

class IndicadorRepository(
    private val dao: IndicadorDAO,
    private val apiService: IndicadoresApiService
) {
    //    Referenciamos al Firebase primero:
    private val indexDataBase: DatabaseReference =
        FirebaseDatabase.getInstance().getReference("indicadores")

    //    funpara agregar datos a API firebase
    fun addIndex(indicador: Indicador, onResult: (Boolean) -> Unit) {
        val key = indexDataBase.push().key ?: return
        val indexWithId = indicador.copy(id_indicador = key)
        indexDataBase.child(key).setValue(indexWithId)
            .addOnCompleteListener {
                onResult(it.isSuccessful)
            }
    }

    //    fun para obtener datos de API firebase
    fun getIndex(onResult: (List<Indicador>?) -> Unit) {
        indexDataBase.get().addOnSuccessListener {
            val indexes: List<Indicador> = it.children.map { dataSnapshot ->
                dataSnapshot.getValue(Indicador::class.java)!!
            }
            onResult(indexes)
        }
    }


//    FIn ref a Firebase

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

    // Funciones que solo interactúan con la API Service externa.
//    Esta fun fetchMeds, recibe todos los indicadores desde la Nube y con el Dao los inserta a la BD Local.
//    Esta fun se llama desde el ViewModel:
    fun fetchIndicadores(): Flow<List<Indicador>> = flow {
        val indicadores = apiService.obtenerIndicadores("indicador")
        indicadores.forEach { indicador ->
            val index = Indicador(
                id = indicador.id,
                id_indicador = indicador.id_indicador,
                codigo = indicador.codigo,
                nombre = indicador.nombre,
                unidad_medida = indicador.unidad_medida,
                serie = indicador.serie,
                imagenUrl = indicador.imagenUrl
            )
            dao.insertIndicador(index)
        }
        emit(indicadores)
        //Con emit se liberan los datos:

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