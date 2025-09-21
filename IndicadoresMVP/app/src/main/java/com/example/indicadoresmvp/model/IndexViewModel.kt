package com.example.indicadoresmvp.model

import android.content.Context
import android.util.Log.e
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.PrimaryKey
import com.example.indicadoresmvp.repository.IndicadorRepository
import com.example.indicadoresmvp.room.AppDatabase
import com.example.indicadoresmvp.room.Indicador
import com.example.indicadoresmvp.service.IndicadoresApiService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class IndexViewModel(private val context: Context) : ViewModel() {
    private var _indicadores = MutableStateFlow<List<Indicador>>(emptyList())
//    val indicadores se usa para almacenar la lista de indicadores desde la API:
    val indicadores: StateFlow<List<Indicador>> = _indicadores

    init {
        getIndexDeApiAndSaveToDb(
            IndicadorRepository(AppDatabase.getDatabase(context).indicadorDao(),
                IndicadoresApiService.ApiInstance.api)
        )
    }

//    Fun que obtiene la lista completa de indicadores desde la Api y la guarda en la BD local:
    private fun getIndexDeApiAndSaveToDb(repository: IndicadorRepository): List<Indicador>? {
        viewModelScope.launch {
            try {
                repository.fetchIndicadores().collect {
                    _indicadores.value = it
                }

            } catch (e: Exception) {
                e.printStackTrace()
            }

        }
        return _indicadores.value
    }


    /*private fun getIndicadoresDesdeApi(): List<Indicador>? {
        viewModelScope.launch {
            try {
                val result = IndicadoresApiService
                    .ApiInstance
                    .api
                    .obtenerIndicadores()
                _indicadores.value = result
            } catch (e: Exception) {
                val errorMessage = e.message ?: "Error desconocido"
            }
        }
        return _indicadores.value

    }*/

    //    fun que obtiene la lista completa de indicadores desde la BD local,
//    la fun se invoca en el init del model. Y este viewModel se llama desde el MainActivity:
/*    fun getIndicadores(): List<Indicador>? {
        viewModelScope.launch {
            try {
                val db = AppDatabase.getDatabase(context)
                _indicadores.value = db.indicadorDao().getAll().stateIn(this).value
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }
        return _indicadores.value

    }*/
    //Para el menu desplegable:
    //Lista 1:
//    val indexTypeOptions: List<String> = listOf("Nacionales", "Internacionales")

    //Lista 2, de indices economicos disponibles nacionales, esto es del negocio no de la pantalla, por ende va al viewModel::
    val indexNationalOptions: List<String> =
        listOf("uf", "ivp", "ipc", "utm", "imacec", "tpm", "libra_cobre", "tasa_desempleo")

    //Lista 3 de indices economicos disponibles internacionales, esto es del negocio no de la pantalla, por ende va al viewModel::
    val indexInternationalOptions: List<String> =
        listOf("Dolar", "dolar_intercambio", "euro", "bitcoin")

    //Var que recorren  cada lista de indices y la de tipo de indice:, tb es del negocio por ende tb va al viewModel:
    //El viewModel es persistente asi que no requiere by remember:

    var indexType by mutableStateOf("")
    var index by mutableStateOf("")
    var date by mutableStateOf("")


    //Funciones cuando cambia cada valor de las var de las 3 listas:
    fun onIndexTypeChange(newIndexType: String) {
        indexType = newIndexType
    }

    //    Indice a consultar: Dolar, uf, etc
    fun onIndexChange(newIndex: String) {
        index = newIndex
    }

    //    Fecha de la consulta:
    fun onDateChange(newDate: String) {
        date = newDate

    }


    //    La sgte fun getIndex() realizara la parte reactiva:
    private var _businessIndexA = MutableStateFlow<Indicador>(
        Indicador(
            id = 0,
            id_indicador = "",
            codigo = "",
            nombre = "",
            unidad_medida = "",
            serie = emptyList(),
            imagenUrl = ""
        )
    )

    //    Fun para obtener los valores de un indice segun su nombre y fecha, desde la BD Room local:
    val businessIndexA: StateFlow<Indicador> = _businessIndexA
    fun getIndex(index: String) {
        viewModelScope.launch {
            try {
                val db = AppDatabase.getDatabase(context)
                db.indicadorDao().getIndicadorByName(index)
//                val result = IndicadoresApiService
//                    .ApiInstance
//                    .api
//                    .obtenerIndicadorPorFecha(index, date)
//                _businessIndexA.value = result
            } catch (e: Exception) {
                val errorMessage = e.message ?: "Error desconocido"
            }

        }
    }

    private var _businessIndexB = MutableStateFlow<Indicador>(
        Indicador(
            id = 0,
            id_indicador = "",
            codigo = "",
            nombre = "",
            unidad_medida = "",
            serie = emptyList(),
            imagenUrl = ""
        )
    )

    //    Fun para obtener los valores de un indice segun su nombre y fecha, desde la BD Room local:
    val businessIndexB: StateFlow<Indicador> = _businessIndexB

    fun getIndicador(index: String) {
        viewModelScope.launch {
            try {
                val db = AppDatabase.getDatabase(context)
                db.indicadorDao().getIndicadorByName(index)
//                val result = IndicadoresApiService
//                    .ApiInstance
//                    .api
//                    .obtenerIndicadorPorFecha(index, date)
//                _businessIndexB.value = result
            } catch (e: Exception) {
                val errorMessage = e.message ?: "Error desconocido"
            }
        }

    }





//    Fun que agrega indicadores a la BD local, se invoca desde la vista AddIndicadorScreen:
    fun addIndicador(indicador: Indicador, context: Context) {
        viewModelScope.launch {
            try {
                val db = AppDatabase.getDatabase(context)
                viewModelScope.launch {
                    db.indicadorDao().insertIndicador(indicador)

                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }



    /*fun getIndexOptions(): List<String> {
        return when (indexType) {
            "Nacionales" -> indexNationalOptions
            //Si selecciono Nacionales me muestra opt de la Lista indexNationalOptions

            "Internacionales" -> indexInternationalOptions
            //Si selecciono Internacionales me muestra opt de la Lista indexInternationalOptions

            //En otro casdo es que no se selecciono nada:
            else -> emptyList()
        }


    }*/

    //Mensajes de error, y los pasamos a la fun validateForm():
    var indexErrorMessage: String? by mutableStateOf(null)
    var dateErrorMessage: String? by mutableStateOf(null)

    //Logica para VALIDAR: fun que devuelve un Result<Unit>:
    fun validateForm(): Result<Unit> {
        indexErrorMessage = null
        dateErrorMessage = null

        if (indexType.isEmpty() || index.isEmpty()) {
            indexErrorMessage = "Por favor, seleccione el indicador"
            return Result.failure(Exception(indexErrorMessage))
        }
        if (date.isEmpty()) {
            dateErrorMessage = "Por favor, ingrese una fecha"
            return Result.failure(Exception(dateErrorMessage))
        } else {
            val dateRegex = Regex("\\d{2}-\\d{2}-\\d{4}")
            if (!date.matches(dateRegex)) {
                dateErrorMessage = "Por favor, ingrese una fecha válida"
                return Result.failure(Exception(dateErrorMessage))
            }
        }
        return Result.success(Unit)
    }

/*    fun validateForm(): Result<Unit> {
        //Para borrar los mensajes de error de alguna consulta anterior:
        indexErrorMessage = null
        dateErrorMessage = null
        //Validamos que el indice y la fecha no esten vacios:
        if (index.isEmpty()) {
            indexErrorMessage = "Por favor seleccione un indice"
            return Result.failure(Exception(indexErrorMessage))
        }

        if (date.isEmpty()) {
            dateErrorMessage = "Por favor ingrese una fecha"
            return Result.failure(Exception(dateErrorMessage))
        }
        //Ademas validamos el formato de la fecha dd/mm/aaaa,
        // en dia y mes deben tener 2 digitos y año 4 digitos:
        else {
            val dateRegex = Regex("""^\d{2}/\d{2}/\d{4}$""")
            if (!date.matches(dateRegex)) {
                dateErrorMessage = "Por favor ingrese una fecha valida"
                return Result.failure(Exception(dateErrorMessage))
            }

        }
        //Si no hay errores retornamos success:
        return Result.success(Unit)
    }*/


}