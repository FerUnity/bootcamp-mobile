package com.example.proyectopersonal.model

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

//Se crea esta clase para usar DB SQlite,
// por tanto esta clase hereda de la clase SQLiteOpenHelper:
class AddMedicamentoDBHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        private const val DATABASE_NAME = "medicamentos.db"
        private const val DATABASE_VERSION = 1

    }

    //        fun para crear la tabla medicamentos de la BD
    override fun onCreate(db: SQLiteDatabase?) {
//        Creamos dos tablas: Pero se administran por el mismo DAO:
        //        AddMedicamentoDAO, porque estan relacionadas
        //    La primera tabla llamada meds_list es una tabla de listas de compras de medicamentos:
        //    Con los campos id, listName, listDescription y listCategory:
        var createTable = """
            CREATE TABLE meds_list (
               id INTEGER PRIMARY KEY AUTOINCREMENT,
               listName TEXT,
               listDescription TEXT,
               listCategory TEXT               
           );
       """
        db?.execSQL(createTable)

        //    Y la segunda tabla llamada medicamentos, son todos los medicamentos. No necesariamente los que me interesa comprar:
        //    Con los campos id, nombre, marca, descripcion, precio y categoria.
         createTable = """
           CREATE TABLE medicamentos (
               id INTEGER PRIMARY KEY AUTOINCREMENT,
               nombre TEXT,
               marca TEXT,
               descripcion TEXT,
               precio REAL,
               categoria TEXT,
               medListId INTEGER REFERENCES meds_list(id) 
               //O sea que cada medicamento pertenece a la lista de medicamentos de arriba
               
                
           );
       """.trimIndent()
        db?.execSQL(createTable)
//       Extendemos la BD con una nueva tabla llamada shopping_list:

    }

    //    fun para actualizar la tabla medicamentos de la BD
    override fun onUpgrade(
        db: SQLiteDatabase?,
        oldVer: Int,
        newVer: Int
    ) {
        //Se elimina la tabla medicamentos si existe y se crea nuevamente:
        db?.execSQL("DROP TABLE IF EXISTS medicamentos")
        onCreate(db)

    }

    //    fun para agregar o actualizar los datos de un medicamento en la tabla medicamentos de la BD.
    //Esta fun se llama desde la fun saveMeds() del AddMedicamentoViewModel:
//    Ojo el id no se agrega porque es autoincremental.
//    Para crear los campos de cada medicamento, creamos la var medicamento de tipo ProductData:
    fun addOrUpdateMedicamento(medicamento: ProductData) {
        val db = writableDatabase //Creamos el writable para escribir en la BD
        //    Y para agregar o cambiar un medicamento a la tabla medicamentos de la BD:
        val values = ContentValues().apply {
            put("nombre", medicamento.nombre)
            put("marca", medicamento.marca)
            put("descripcion", medicamento.descripcion)
            put("precio", medicamento.precio)
            put("categoria", medicamento.categoria)
        }
        // Si el medicamento no tiene un id es porque no existe en la BD, lo agregamos o insertamos:
        if (medicamento.id == null) {
            db.insert("medicamentos", null, values)
        // Si el medicamento tiene un id es porque ya existe en la BD, lo actualizamos:
        } else {
            db.update(
                "medicamentos", values,"id = ?",
                arrayOf(medicamento.id.toString())
            )
        }
        db.close()
    }

    //    fun para obtener un medicamento de la tabla medicamentos de la BD, segun su id:
    fun getMedicamentosById(id: Int): ProductData? {
        val db = readableDatabase
        val cursor = db.query(
            "medicamentos",
            arrayOf("id", "nombre", "marca", "descripcion", "precio", "categoria"),
            "id = ?", //EL filtro para obtener el med sera el id
            arrayOf(id.toString()),
            null,
            null,
            null
        )
        //    Para obtener los campos de cada medicamento,
        //    creamos la var medicamento de tipo ProductData.
        //    Primero le decimos que sea null y si el cursor encuentra un medicamento por id, llenara sus campos:
        var medicamento: ProductData? = null
        if (cursor.moveToFirst()) {
            val id = cursor.getInt(cursor.getColumnIndexOrThrow("id"))
            val nombre = cursor.getString(cursor.getColumnIndexOrThrow("nombre"))
            val marca = cursor.getString(cursor.getColumnIndexOrThrow("marca"))
            val descripcion = cursor.getString(cursor.getColumnIndexOrThrow("descripcion"))
            val precio = cursor.getDouble(cursor.getColumnIndexOrThrow("precio"))
            val categoria = cursor.getString(cursor.getColumnIndexOrThrow("categoria"))

            //    Finalmente creamos el medicamento con sus valores de cada campo,
            //    obtenidos por id, del cursor aca arriba.
            medicamento = ProductData(
                id,
                nombre,
                marca,
                descripcion,
                precio,
                categoria)
        }
        cursor.close()
        db.close()
        //    Y retornamos el medicamento para leer sus valores:
        return medicamento
    }

//    fun para obtener una lista de todos los medicamentos de la tabla medicamentos de la BD.
//    Esta fun es llamada desde la fun loadMeds() del AddMedicamentoViewModel:
    fun getMedicamentos(): List<ProductData> {
        val medicamentos = mutableListOf<ProductData>()
        val db = readableDatabase
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
            //    Para obtener una lista de todos los medicamentos de la tabla medicamentos de la BD y sus campos,
            //    creamos la var medicamento de tipo Lista de ProductData
            //    y llenamos sus campos con los valores obtenidos del cursor aca arriba.
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
        }
    }
