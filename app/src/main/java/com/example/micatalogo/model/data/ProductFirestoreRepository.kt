package com.example.micatalogo.model.data

import com.google.firebase.firestore.FirebaseFirestore

//Esta clase tb es solo para interactuar con la BD de Firebase,
//SOLO  a traves de FIRESTORE,
// PERO NO CON REALTIME DATABASE (RTDB)

class ProductFirestoreRepository: ProductRepository {
    //HAcemos que herede de ProductRepository que administra ambos repositorios (RTDB y Firestore),
    // por ende hay que hacer overide(implementar) las fun:

    //    Instancia de la BBDD de Firestore de Firebase con el nombre db:
    private val db = FirebaseFirestore.getInstance()

//    Luego obtenemos la coleccion de productos de la BBDD de Firestore de Firebase,
//    pero sin una estructura jerarquica:
    private val productsCollection = db.collection("products")

//    fun para agregar datos a la BBDD de Firestore de la API firebase:
    override fun addProduct(
        product: Producto,
        onResult: (Boolean) -> Unit
    ) {
        productsCollection.add(product)
            .addOnSuccessListener {
//                SI es exitosa la subida de prod al repo y por ende al Api firebase,
//                obtenemos un resultado positivo: onResult(true)
                onResult(true)
            }
            .addOnFailureListener {
//                SI NO es exitosa la subida de prod al repo y por ende al Api firebase,
//                obtenemos un resultado negativo: onResult(false)
                onResult(false)
            }


    }

//    fun para obtener datos de la BBDD del Firestore de la API firebase:
    override fun getProducts(onResult: (List<Producto>) -> Unit) {
        TODO("Not yet implemented")
    }


}