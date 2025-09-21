package com.example.micatalogo.viewmodel

import com.example.micatalogo.model.data.ProductRealtimeRepository
import com.example.micatalogo.model.data.Producto
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ProductViewModel(private val repository: ProductRealtimeRepository = ProductRealtimeRepository()) {
    private val _products = MutableStateFlow<List<Producto>>(emptyList())
    val products: StateFlow<List<Producto>> = _products as StateFlow<List<Producto>>

//  Es comno redundante que se envien y obtengan productos hacia y desde el repositorio y de ahi a la Api firebase,
//    Pero asi no cambiamos el viewmodel y solo cambiamos el repositorio.
//    El viewmodel se ABSTRAE de la API firebase y nunca se enetera donde se aloja la info de los productos:

//    fun para agregar productos al repositorio,
//    luego esta informacion del prod se enviara a la API firebase con una fun de repository que tb se llama addProduct:
    fun addProduct(product: Producto) {
        repository.addProduct(product) { success ->
//            Si es exitosa la subida de prod al repo y por ende al Api firebase,
//            obtenemos nuevamente la lista de productos actualizada del repo y del Api firebase:
            if (success) {
                getProducts()
            }
        }
    }

    //    fun para obtener productos del repositorio,
    //    los cuales se obtienen de la API firebase con una fun de repository que tb se llama getProducts.
    //    Se obtienen los prod antes de que sean mostrados en la UI:
    fun getProducts() {
        repository.getProducts { products ->
            _products.value = products
        }
    }


}