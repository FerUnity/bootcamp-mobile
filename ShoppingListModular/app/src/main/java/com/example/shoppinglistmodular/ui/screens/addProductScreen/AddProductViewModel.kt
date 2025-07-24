package com.example.shoppinglistmodular.ui.screens.addProductScreen


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class AddProductViewModel : ViewModel() {
    var productName by mutableStateOf("")
        private set

    fun onProductNameChange(value: String) {
        productName = value
    }

    var productBrand by mutableStateOf("")
        private set

    fun onProductBrandChange(value: String) {
        productBrand = value
    }

    var productDescription by mutableStateOf("")
        private set

    fun onProductDescriptionChange(value: String) {
        productDescription = value
    }

    var productPrice by mutableStateOf("")
        private set

    fun onProductPriceChange(value: String) {
        productPrice = value
    }

    var productCategory by mutableStateOf("")
        private set

    fun onProductCategoryChange(value: String) {
        productCategory = value
    }

    fun addProduct() {
        // Lógica para guardar el producto
    }
}