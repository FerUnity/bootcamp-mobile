package com.example.proyectopersonal.model

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

//    SI SE USA CON ROOM:
//    Luego tiene que haber 1 @Dao por data class o Entidad,
//    esta interface SOLO ADMINISTRA LA TABLA DE MEDICAMENTOS CON ProductData.kt.
//    Room automaticamente implementa la comunicacion con la BD. Usando
//    las anotaciones @Query, @Inser, @Update, @Delete le indican a Room que hacer con cada funcion que sigue abajo.
//    Ojo ademas cada fun debe funcionar como asincrona con corrutina(suspend) para que no bloquee la interfaz con el usuario:
@Dao
interface ProductDAO {

//    Agregar un medicamento (product: ProductData) a la tabla medicamentos de la BD:
    @Insert
    suspend fun insertMedicamento(medicamento: ProductData): Long

//    Obtener una lista de medicamentos (medListId: Int) desde una lista de compras de medicamentos de la BD,
    //    que tenga como valor de id = medListId:
    @Query("SELECT * FROM medicamentos WHERE medListId = :medListId")
    suspend fun getMedicamentos(medListId: Int): List<ProductData>

//    Obtener un medicamento(medicamento: ProductData) de la tabla medicamentos de la BD, segun su id:
    @Query("SELECT * FROM medicamentos WHERE id = :id")
    suspend fun getMedicamentosById(id: Int): ProductData?

//    Obtener una lista de medicamentos (medListId: Int) de una lista de compras de medicamentos de la BD,
    //    que tenga como valor de id = medListId y segun su categoria:
    @Query("SELECT * FROM medicamentos WHERE medListId = :medListId AND categoria = :category")
    suspend fun getMedicamentosByCategory(medListId: Int, category: String): List<ProductData>

//    Actualizar un medicamento (medicamento: ProductData) de la tabla medicamentos de la BD:
    @Update
    suspend fun updateMedicamento(medicamento: ProductData): Long

    //    Eliminar un medicamento (medicamento: ProductData) de la tabla medicamentos de la BD:
    @Delete
    suspend fun removeMedicamento(medicamento: ProductData): Long


}