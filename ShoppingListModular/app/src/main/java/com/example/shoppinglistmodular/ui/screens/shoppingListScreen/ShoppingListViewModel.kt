package com.example.shoppinglistmodular.ui.screens.shoppingListScreen


import androidx.lifecycle.ViewModel

class ShoppingListViewModel : ViewModel() {
    val products = mutableListOf<ProductData>()
        private set

    fun addProduct(product: ProductData) {
        products.add(product)
    }

    fun removeProduct(product: ProductData) {
        products.remove(product)
    }

    fun updateProduct(product: ProductData) {
        val index = products.indexOfFirst { it.id == product.id }
        if (index != -1) {
            products[index] = product
        }
    }

    fun getProductById(id: Int): ProductData? {
        return products.find { it.id == id }
    }
}