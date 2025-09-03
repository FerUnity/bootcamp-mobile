package com.example.proyectopersonal.ui.screens.addMedicamentoScreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel

class AddMedicamentoViewModel: ViewModel() {

    val medsTypeOptions: List<String> = listOf("Orales: comprimidos", "Tópicos: pomadas", "Ópticos: gotas para los ojos",
        "Intravenosos o intramusculares: viales", "Intradérmicos: insulina")


    //Lista 2, de indices economicos disponibles nacionales, esto es del negocio no de la pantalla, por ende va al viewModel::
    val orales: List<String> = listOf("ABACAVIR", "ACETAMINOFÉN", "Ácido ACETILSALICÍLICO", "ACICLOVIR")

    //Lista 3 de indices economicos disponibles internacionales, esto es del negocio no de la pantalla, por ende va al viewModel::
    val pomadas: List<String> = listOf("Voltadol Forte","Zovicrem","Blastoestimulina","Traumeel S","Radio Salil")

    val opticos: List<String> = listOf("neomicina","polimixina","bacitracina")

    val intravenosos: List<String> = listOf("Tylenol", "Epinefrina", "Ampicilina", "Anfotericina B", "Dexametasona")

    val intradermicos: List<String> = listOf("Vacuna contra la hepatitis B","Vacuna contra el tétanos","Vacuna contra el neumococo")

    var medsType by mutableStateOf("")

    fun getMedsOptions(): List<String> {
        return when (medsType) {
            "Orales: comprimidos" -> orales
            //Si selecciono Nacionales me muestra opt de la Lista indexNationalOptions

            "Tópicos: pomadas" -> pomadas
            //Si selecciono Internacionales me muestra opt de la Lista indexInternationalOptions

            "Ópticos: gotas para los ojos" -> opticos

            "Intravenosos o intramusculares: viales" -> intravenosos

            "Intradérmicos: insulina" -> intradermicos

            //En otro caso es que no se selecciono nada:
            else -> emptyList()
        }


    }

    var index by mutableStateOf("")
        private set
    var productName by mutableStateOf("")
        private set

    //Funciones cuando cambia cada valor de las var de las 3 listas:
    fun onMedsTypeChange(newMedsType: String) {
        medsType = newMedsType
    }



    fun onIndexChange(newIndex: String) {
        index = newIndex
    }

    //Y cuando haya un cambio en el valor de productName usamos la fun onProductNameChange:
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

    //Aca guardamos los 3 errores de las validaciones de mas abajo:
    //Los errores son  mensajes de texto(string), sino hay error el valor es null
    var productNameError by mutableStateOf<String?>(null)
    var productBrandError by mutableStateOf<String?>(null)
    var productPriceError by mutableStateOf<String?>(null)

    //Y una var bool que define si el formulario es valido, por defecto no lo es:
    var isFormValid by mutableStateOf(false)

    //Y creamos una fun para validar el formulario,
    // creando valores(val) que rep el retrorno de las fun de validacion de los 3 campos:
    fun validateForm(){
        val productNameValidation = validateProductName(productName)
        val productBrandValidation = validateProductBrand(productBrand)
        val productPriceValidation = validateProductPrice(productPrice)

        productNameError = if (productNameValidation is validateInput.Error){
            productNameValidation.message
            //Se pone else porque si queremos validar varias veces hay que borra el campor anterior
        } else {
            null
        }
        productBrandError = if (productBrandValidation is validateInput.Error){
            productBrandValidation.message
        } else {
            null
        }
        productPriceError = if (productPriceValidation is validateInput.Error){
            productPriceValidation.message
            //Se pone else porque si queremos validar varias veces hay que borra el campor anterior
        } else {
            null

        }

        //El formulario sera valido si los 3 errores son nulos, o sea los 3 campos del prod son validos:
        //isFormValid = productNameError == null && productBrandError == null && productPriceError == null
        //O mejor asi:
        isFormValid = productNameValidation is validateInput.Success &&
                productBrandValidation is validateInput.Success &&
                productPriceValidation is validateInput.Success

    }
    fun addProduct(savedStateHandle: SavedStateHandle?) {
        // Preparamos la respuesta cuando se vuelva a la pantalla principal luego de agregar un producto, creo:
        savedStateHandle?.set("productName", productName)
        savedStateHandle?.set("productBrand", productBrand)
        savedStateHandle?.set("productDescription", productDescription)
        savedStateHandle?.set("productPrice", productPrice)
        savedStateHandle?.set("productCategory", productCategory)

    }


    sealed class validateInput {
        object Success: validateInput()
        data class Error(val message: String): validateInput()

    }

    //Y comenzamos a validar cada var o campo del producto,
    // usando la sealed class validateInput que definimos arriba:

    fun validateProductName(productName: String): validateInput {
        if (productName.isEmpty()) {
            //Si hay error usamos el dataclass
            return validateInput.Error("El nombre del producto no puede estar vacío")
        }
        //Si no hay error usamos el object Success del validateInput:
        return validateInput.Success
    }

    fun validateProductBrand(productBrand: String): validateInput {
        if (productBrand.isEmpty()) {
            return validateInput.Error("La marca del producto no puede estar vacía")
        }
        return validateInput.Success
    }

    fun validateProductPrice(productPrice: String): validateInput {
        if (productPrice.isEmpty()) {
            return validateInput.Error("El precio del producto no puede estar vacío")
        }
        //Si el precio ingresado no es un valor numerico,
        // ademas se usa double porque en otros paises se usa el precio con decimales:
        else if (productPrice.toDoubleOrNull() == null) {
            return validateInput.Error("El precio del producto debe ser un número")
        }
        else{
            return validateInput.Success

        }


    }
}