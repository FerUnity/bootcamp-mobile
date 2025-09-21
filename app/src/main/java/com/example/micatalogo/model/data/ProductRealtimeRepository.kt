package com.example.micatalogo.model.data

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

//Esta clase es solo para interactuar con la BD de Firebase,
//SOLO  a traves de REALTIME DATABASE (RTDB),
// PERO NO CON FIRESTORE
class ProductRealtimeRepository: ProductRepository {
    //HAcemos que herede de ProductRepository que administra ambos repositorios (RTDB y Firestore),
    // por ende hay que hacer overide(implementar) las fun:
//    Abrimos una instancia de la RTDB de Firebase como db:
    private val db = FirebaseDatabase.getInstance()

//    Luego hacemos una instancia de la estructrura jerarquica del json de la BD de Firebase, para que quede ordenado como el json:
    private val productos = db.getReference("productos")

    //    fun para agregar datos a RTDB de la API firebase
    override fun addProduct(product: Producto, onResult: (Boolean) -> Unit) {
        val key = productos.push().key ?: return
        val productWithId = product.copy(id = key)
        productos.child(key).setValue(productWithId)
            .addOnCompleteListener {
                onResult(it.isSuccessful)
            }
    }

    //    fun para obtener datos de RTDB de la API firebase:
    override fun getProducts(onResult: (List<Producto>) -> Unit) {
        productos.get().addOnSuccessListener {
            val products = it.children.map { dataSnapshot ->
                dataSnapshot.getValue(Producto::class.java)!!
            }
            onResult(products)
        }
        }

}