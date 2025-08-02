package com.example.proyectopersonal.ui.screens.IndexScreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class IndexViewModel: ViewModel() {
    //Para el menu desplegable:
    //Lista 1:
    val indexTypeOptions: List<String> = listOf("Hospitales", "Especialidades")

    //Lista 2, de indices economicos disponibles nacionales, esto es del negocio no de la pantalla, por ende va al viewModel::
    val hospitalCategories: List<String> = listOf("Mutual", "San Borja", "JJAguirre", "San Juan De Dios")

    //Lista 3 de indices economicos disponibles internacionales, esto es del negocio no de la pantalla, por ende va al viewModel::
    val especialidadCategories: List<String> = listOf("Medicina General", "Odontologia", "Cardiologia", "Pediatria", "Ginecologia")

    //Var que recorren  cada lista de indices y la de tipo de indice:, tb es del negocio por ende tb va al viewModel:
    //El viewModel es persistente asi que no requiere by remember:

    var indexType by mutableStateOf("")
    var index by mutableStateOf("")


    //Funciones cuando cambia cada valor de las var de las 3 listas:
    fun onIndexTypeChange(newIndexType: String) {
        indexType = newIndexType
    }

    fun onIndexChange(newIndex: String) {
        index = newIndex
    }



    fun getIndexOptions(): List<String> {
        return when (indexType) {
            "Hospitales" -> hospitalCategories
            //Si selecciono Nacionales me muestra opt de la Lista indexNationalOptions

            "Especialidades" -> especialidadCategories
            //Si selecciono Internacionales me muestra opt de la Lista indexInternationalOptions

            //En otro casdo es que no se selecciono nada:
            else -> emptyList()
        }


    }

    //Mensajes de error, y los pasamos a la fun validateForm():
    var indexErrorMessage: String? by mutableStateOf(null)
    //var dateErrorMessage: String? by mutableStateOf(null)

    //Logica para VALIDAR: fun que devuelve un Result<Unit>:

    fun validateForm(): Result<Unit> {
        //Para borrar los mensajes de error de alguna consulta anterior:
        indexErrorMessage = null
        //dateErrorMessage = null
        //Validamos que el indice y la fecha no esten vacios:
        if (index.isEmpty()) {
            indexErrorMessage = "Por favor seleccione una opcion"
            return Result.failure(Exception(indexErrorMessage))
        }
        //Si no hay errores retornamos success:
        return Result.success(Unit)
    }
}