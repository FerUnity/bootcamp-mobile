package com.example.micatalogo.model.data

//ESTA INTERFACE ES PARA ADMINISTRAR AMBOS REPOSITORY (RTDB Y FIRESTORE) QUE SE CONECTAN CON LA API FIREBASE,

interface ProductRepository {
    //    fun para agregar datos a Firestore de la API firebase
    fun addProduct(product: Producto, onResult: (Boolean) -> Unit)

    //    fun para obtener datos de Firestore de la API firebase:
    fun getProducts(onResult: (List<Producto>) -> Unit)
}