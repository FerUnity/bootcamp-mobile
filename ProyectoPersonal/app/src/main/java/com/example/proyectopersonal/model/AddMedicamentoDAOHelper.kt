package com.example.proyectopersonal.model

import android.content.ContentValues

class AddMedicamentoDAOHelper(var dbHelper: AddMedicamentoDBHelper) : MedShoppingListDAO {
    override fun createList(list: MedsListData): Long {
        TODO("Not yet implemented")
    }

    override fun updateList(list: MedsListData): Long {
        TODO("Not yet implemented")
    }

    override fun addMedicamento(medicamento: ProductData): Long {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put("nombre", medicamento.nombre)
            put("marca", medicamento.marca)
            put("descripcion", medicamento.descripcion)
            put("precio", medicamento.precio)
            put("categoria", medicamento.categoria)

        }
        val response: Long = db.insert("medicamentos", null, values)
        db.close()
        return response
    }

    override fun getMedicamentosById(id: Int): ProductData? {
        val db = dbHelper.readableDatabase
        val cursor = db.query(
            "medicamentos",
            arrayOf("id", "nombre", "marca", "descripcion", "precio", "categoria"),
            "id = ?",
            arrayOf(id.toString()),
            null,
            null,
            null
        )

        var medicamento: ProductData? = null
        if (cursor.moveToFirst()) {
            val id = cursor.getInt(cursor.getColumnIndexOrThrow("id"))
            val nombre = cursor.getString(cursor.getColumnIndexOrThrow("nombre"))
            val marca = cursor.getString(cursor.getColumnIndexOrThrow("marca"))
            val descripcion = cursor.getString(cursor.getColumnIndexOrThrow("descripcion"))
            val precio = cursor.getDouble(cursor.getColumnIndexOrThrow("precio"))
            val categoria = cursor.getString(cursor.getColumnIndexOrThrow("categoria"))
            medicamento = ProductData(
                id,
                nombre,
                marca,
                descripcion,
                precio,
                categoria
            )
        }

            cursor.close()
            db.close()
            return medicamento
    }

    override fun getMedicamentos(medListId: Int): List<ProductData> {
        TODO("Not yet implemented")

    }

    override fun getMedicamentosByCategory(
        medListId: Int,
        category: String
    ): List<ProductData> {
        TODO("Not yet implemented")
    }

    /* override fun getMedicamentos(): List<ProductData> {
         val medicamentos = mutableListOf<ProductData>()
         val db = dbHelper.readableDatabase
         val cursor = db.query(
             "medicamentos",
             arrayOf("id", "nombre", "marca", "descripcion", "precio", "categoria"),
             null,
             null,
             null,
             null,
             null
         )
         while (cursor.moveToNext()) {
             val id = cursor.getInt(cursor.getColumnIndexOrThrow("id"))
             val nombre = cursor.getString(cursor.getColumnIndexOrThrow("nombre"))
             val marca = cursor.getString(cursor.getColumnIndexOrThrow("marca"))
             val descripcion = cursor.getString(cursor.getColumnIndexOrThrow("descripcion"))
             val precio = cursor.getDouble(cursor.getColumnIndexOrThrow("precio"))
             val categoria = cursor.getString(cursor.getColumnIndexOrThrow("categoria"))
             val medicamento = ProductData(
                 id,
                 nombre,
                 marca,
                 descripcion,
                 precio,
                 categoria
             )
             medicamentos.add(medicamento)
         }
         cursor.close()
         db.close()
         return medicamentos

     }*/

    override fun updateMedicamento(medicamento: ProductData): Long {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put("nombre", medicamento.nombre)
            put("marca", medicamento.marca)
            put("descripcion", medicamento.descripcion)
            put("precio", medicamento.precio)
            put("categoria", medicamento.categoria)
        }
        val response: Long = db.update(
            "medicamentos",
            values,
            "id = ?",
            arrayOf(medicamento.id.toString())
        ).toLong()
        db.close()
        return response


    }

    override fun removeMedicamento(medicamento: ProductData): Long {
        val db = dbHelper.writableDatabase
        val response: Long = db.delete(
            "medicamentos",
            "id = ?",
            arrayOf(medicamento.id.toString())
        ).toLong()
        db.close()
        return response
    }

}



