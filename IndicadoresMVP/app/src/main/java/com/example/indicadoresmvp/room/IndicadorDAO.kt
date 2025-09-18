package com.example.indicadoresmvp.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

//    SI SE USA CON ROOM:
//    Luego tiene que haber 1 @Dao por data class o Entidad,
//    esta interface DAO define las operaciones de CRUD (Create, Read, Update, Delete) que se pueden realizar en la tabla de indicadores.
//    SOLO ADMINISTRA LA TABLA DE INDICADORES CON el ProductData = Indicador.kt.
//    Room automaticamente implementa la comunicacion con la BD LOCAL. Usando
//    las anotaciones @Query, @Inser, @Update, @Delete le indica a Room que hacer con cada funcion que sigue abajo.
//    Ojo ademas cada fun debe funcionar como asincrona con corrutina(suspend) para que no bloquee la interfaz con el usuario:
@Dao
interface IndicadorDAO {
    //    Agregar un medicamento (product: ProductData) a la tabla medicamentos de la BD:
    @Insert
    suspend fun insertIndicador(indicador: Indicador): Long

    //    Obtener una lista de medicamentos (medListId: Int) desde una lista de compras de medicamentos de la BD,
    //    que tenga como valor de id = medListId:
    @Query("SELECT * FROM indicadores")
    fun getAll(): Flow<List<Indicador>>

    //    Obtener un medicamento(medicamento: ProductData) de la tabla medicamentos de la BD, segun su id:
    @Query("SELECT * FROM indicadores WHERE id = :id")
    suspend fun getIndicadoresById(id: Int): Indicador?

    //    Obtener una lista de medicamentos (medListId: Int) de una lista de compras de medicamentos de la BD,
    //    que tenga como valor de id = medListId y segun su categoria:
//    @Query("SELECT * FROM indicadores WHERE  = :medListId AND categoria = :category")
//    suspend fun getIndicadoresByCategory(medListId: Int, category: String): List<Indicador>

    //    Actualizar un medicamento (medicamento: ProductData) de la tabla medicamentos de la BD:
    @Update
    suspend fun updateIndicador(indicador: Indicador): Unit

    //    Eliminar un medicamento (medicamento: ProductData) de la tabla medicamentos de la BD:
    @Delete
    suspend fun removeIndicador(indicador: Indicador): Unit
}