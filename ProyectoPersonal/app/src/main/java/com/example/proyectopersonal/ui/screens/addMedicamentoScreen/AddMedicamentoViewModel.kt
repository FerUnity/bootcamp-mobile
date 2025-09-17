package com.example.proyectopersonal.ui.screens.addMedicamentoScreen

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.proyectopersonal.model.AppDatabase
import com.example.proyectopersonal.model.MedsListData
import com.example.proyectopersonal.model.ProductData
import com.example.proyectopersonal.room.MedRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

//SI LO USAREMOS CON ROOM: CAMBIAMOS VRAIAS COSAS
//Gestiona el estado de la interfaz de usuario, orquesta la logica de negocio y provee datos a la vista.
//Ademas decide cuando consultar al Repositorio

class AddMedicamentoViewModel(private val context: Context) : ViewModel() {
    /*    companion object {
        lateinit var medDaoHelper: AddMedicamentoDAOHelper
    }*/

    //    ROOM:
    private var _medicamentos = MutableStateFlow<List<ProductData>>(emptyList())
//        private set //Para que tenga gettter y setter y sea propio de esta clase

    var medicamentos: StateFlow<List<ProductData>> = _medicamentos

//    Al invocarse AddMedicamentoViewModel(context: Context) desde el onCreate() del MainActivity,
//    de inmediato se ejecuta esta fun init, que invoca a la fun getAll() del ProductDAO,
//    la cual carga la lista de medicamentos desde la BD, y la almacena en la var _medicamentos,
//    que es la lista de medicamentos local, para tenerla disponible para la vista:

    //    Pero ahora haremos lo mismo que dice arriba (getAll() del ProductDAO)de la BD local,
//    para que sea mas sencillo y modularizado, con la fun getMedicamentos() def mas abajo(101):
    init {
        getMedicamentos()
    }

    var medsList = mutableListOf<MedsListData>()
        private set


    //    El DatabaseBuilder es un objeto singleton que se utiliza para obtener una instancia de la base de datos,
    //    en tiempo de ejecucion de la app
    /* object DatabaseBuilder {
         //Creamos una var de clase BD que referencia a la clase abstracta AppDatabase, por ende a la BD Sqlite:
         private var db: AppDatabase? = null

         //    Obtenemos una instancia de la BD para que sea sigleton:
         fun getInstance(context: Context): AppDatabase {
             if (db == null) {
                 db = Room.databaseBuilder(
                     context.applicationContext,
                     AppDatabase::class.java,
                     //Aca ponemos el nombre de la BD:
                     AppDatabase.DATABASE_NAME
                 ).build()
             }
             return db!!

         }
     }*/

//    Cargar la lista de medicamentos ya guardados en la BD:
    /* fun loadMeds(context: Context) {
         //Obtenemos una instancia de la BD
         val db = DatabaseBuilder.getInstance(context)
 //     Obtenemos su productDao
         val productDao = db.productDao()
         CoroutineScope(Dispatchers.IO).launch {
             Log.d("AddMedicamentoViewModel", "Cargando lista de medicamentos...")
 //            Obtenemos y cargo la lista de medicamentos desder el DAO,
             val m: List<ProductData> = productDao.getMedicamentos(0)
             _medicamentos = MutableLiveData(m)


         }
     }*/

//    Cargar la lista de compras de medicamentos ya guardados en la BD:
    /*fun loadMedsList(context: Context) {
        //Obtenemos una instancia de la BD
        val db = DatabaseBuilder.getInstance(context)
//     Obtenemos su productDao
        val medShoppingListDao = db.medShoppingListDao()
        CoroutineScope(Dispatchers.IO).launch {
//            Obtenemos y cargo la lista de compras de medicamentos desder el DAO,
            //     que tenga como valor de id = medListId
            medsList = medShoppingListDao.getMedShoppingLists().toMutableList()


        }
    }*/

    fun getMedicamentos(): List<ProductData>? {
        viewModelScope.launch {
            try {
                val db = AppDatabase.getDatabase(context)
                _medicamentos.value = db.productDao().getAll().stateIn(this).value
            } catch (e: Exception) {
                e.printStackTrace()
            }

            }

        return _medicamentos.value


        }

        fun addMedicamento(medicamento: ProductData, context: Context) {
            try {
                val db = AppDatabase.getDatabase(context)
                viewModelScope.launch {
                    db.productDao().insertMedicamento(medicamento)
                }

            } catch (e: Exception) {
                e.printStackTrace()
            }


        }


//    Para guardar la lista de medicamentos en la BD al cerrar la app:
        /* fun addProductFromHandler(savedStateHandle: SavedStateHandle?) {
             val id = savedStateHandle?.get<Int>("id") ?: 0
             val medicamento = ProductData(
                 id = id,
                 nombre = savedStateHandle?.get<String>("productName") ?: "",
                 marca = savedStateHandle?.get<String>("productBrand") ?: "",
                 descripcion = savedStateHandle?.get<String>("productDescription") ?: "",
                 precio = savedStateHandle?.get<Double>("productPrice") ?: 0.0,
                 categoria = savedStateHandle?.get<String>("productCategory") ?: "",
                 medListId = 0
             )
             val db = DatabaseBuilder.getInstance(savedStateHandle!!.get<Context>("context")!!)
             val productDao = db.productDao()
             CoroutineScope(Dispatchers.IO).launch {
                 productDao.insertMedicamento(medicamento)
             }
     //        medicamentos.add(medicamento)
         }*/


        val medsTypeOptions: List<String> = listOf(
            "Orales: comprimidos", "Tópicos: pomadas", "Ópticos: gotas para los ojos",
            "Intravenosos o intramusculares: viales", "Intradérmicos: insulina"
        )


        //Listas de categorias de meds, esto es del negocio no de la pantalla, por ende va al viewModel::
        val orales: List<String> =
            listOf("ABACAVIR", "ACETAMINOFÉN", "Ácido ACETILSALICÍLICO", "ACICLOVIR")

        //Lista 3 de indices economicos disponibles internacionales, esto es del negocio no de la pantalla, por ende va al viewModel::
        val pomadas: List<String> =
            listOf("Voltadol Forte", "Zovicrem", "Blastoestimulina", "Traumeel S", "Radio Salil")

        val opticos: List<String> = listOf("neomicina", "polimixina", "bacitracina")

        val intravenosos: List<String> =
            listOf("Tylenol", "Epinefrina", "Ampicilina", "Anfotericina B", "Dexametasona")

        val intradermicos: List<String> = listOf(
            "Vacuna contra la hepatitis B",
            "Vacuna contra el tétanos",
            "Vacuna contra el neumococo"
        )

        var medsType by mutableStateOf("")

        fun getMedsOptions(): List<String> {
            return when (medsType) {
                "Orales: comprimidos" -> orales
                //Si selecciono Orales: comprimidos me muestra opt de la Lista orales

                "Tópicos: pomadas" -> pomadas

                "Ópticos: gotas para los ojos" -> opticos

                "Intravenosos o intramusculares: viales" -> intravenosos

                "Intradérmicos: insulina" -> intradermicos

                //En otro caso es que no se selecciono nada:
                else -> emptyList()
            }

        }

        var medsTypePrice by mutableStateOf("")
        fun getMedPrice(): String {
            return when (medsTypePrice) {
                "ABACAVIR" -> "1000.0"
                "ACETAMINOFÉN" -> "3000.0"
                "Ácido ACETILSALICÍLICO" -> "5000.0"
                "ACICLOVIR" -> "2000.0"

                "Voltadol Forte" -> "3000.0"
                "Zovicrem" -> "4000.0"
                "Blastoestimulina" -> "5000.0"
                "Traumeel S" -> "30000.0"
                "Radio Salil" -> "15000.0"

                "neomicina" -> "23000.0"
                "polimixina" -> "33000.0"
                "bacitracina" -> "83000.0"

                "Tylenol" -> "43000.0"
                "Epinefrina" -> "53000.0"
                "Ampicilina" -> "63000.0"
                "Anfotericina B" -> "93000.0"
                "Dexametasona" -> "83000.0"


                else -> "0.0"


            }

        }

        var index by mutableStateOf("")
        private set

        //    var indexPrice by mutableStateOf("")
//        private set
        var productName by mutableStateOf("")
        private set

        //Funciones cuando cambia cada valor de las var de las 3 listas:
        fun onMedsTypeChange(newMedsType: String) {
            medsType = newMedsType
        }

        fun onMedsTypePriceChange(newMedsTypePrice: String) {
            medsTypePrice = newMedsTypePrice
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
        fun validateForm() {
            val productNameValidation = validateProductName(productName)
            val productBrandValidation = validateProductBrand(productBrand)
            val productPriceValidation = validateProductPrice(productPrice)

            productNameError = if (productNameValidation is validateInput.Error) {
                productNameValidation.message
                //Se pone else porque si queremos validar varias veces hay que borra el campor anterior
            } else {
                null
            }
            productBrandError = if (productBrandValidation is validateInput.Error) {
                productBrandValidation.message
            } else {
                null
            }
            productPriceError = if (productPriceValidation is validateInput.Error) {
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

        fun addProduct(context: Context, savedStateHandle: SavedStateHandle?) {
            //Al agregar el medicamento a la lista local, lo guardamos en la base de datos SQLite, CREO:
//        saveMeds(context)
            // Preparamos la respuesta cuando se vuelva a la pantalla principal luego de agregar un producto, creo:
            savedStateHandle?.set("productName", productName)
            savedStateHandle?.set("productBrand", productBrand)
            savedStateHandle?.set("productDescription", productDescription)
            savedStateHandle?.set("productPrice", productPrice)
            savedStateHandle?.set("productCategory", productCategory)

        }


        sealed class validateInput {
            object Success : validateInput()
            data class Error(val message: String) : validateInput()

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
            } else {
                return validateInput.Success

            }


        }


        //  NO VA CON ROOM LO SIGUIENTE:
//  fun para guardar en la base de datos SQLite.
//   Inicializamos aca el medDbHelper:
        /*fun getDao(context: Context) {
            medDaoHelper = AddMedicamentoDAOHelper  (AddMedicamentoDBHelper(context))
        }*/


        //Para usar con JSON o SQLite:

//    La fun loadMeds() se usa para cargar los datos desde la base de datos SQLite hacia la lista local medicamentos.
//    Esta fun se invoca desde la fun onCreated() del MainActivity:
        /* fun loadMeds(context: Context) {
            *//*        val json = "{medicamentos: []}"
                try {
        //        guardamos el contenido del arch json en la var json: cono texto:
                val json: String = context.assets.open("medicamentos.json").bufferedReader().use {
                    it.readText()
                }
                //Para interpretar el arch json usamos la lib Gson:
                val medicamentos = Gson().fromJson(json, T::class.java)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
                val medList = JSONObject(json)
                val medArray: JSONArray = medList.getJSONArray("medicamentos")
                for (i in 0 until medArray.length()) {
                    val medicamento = medArray.getJSONObject(i)
                    var id = medicamento.getInt("id")
                    val nombre = medicamento.getString("nombre")
                    val marca = medicamento.getString("marca")
                    val descripcion = medicamento.getString("descripcion")
                    val precio: Float? = medicamento.getDouble("precio").toFloat()
                    val categoria = medicamento.getString("categoria")
                    val productData = ProductData(
                        id = id,
                        nombre = nombre,
                        marca = marca,
                        descripcion = descripcion,
                        precio = precio,
                        categoria = categoria
                    )
                    medicamentos.add(productData)

                }*//*
        //Luego para cargar los datos desde la base de datos SQLite con la lista de medicamentos:

//        Obtenemos los datos de los medicamentos de la base de datos SQLite
        //y los guardamos en una nueva lista llamada val medicamentos:
        val medicamentos: List<ProductData> = medDaoHelper.getMedicamentos(0)
        //Y luego estos medicamentos obtenidos de la base de datos SQLite,
    // los agregamos a la lista de medicamentos local: this.medicamentos:
        this.medicamentos = medicamentos.toMutableList()

    }


    //   La fun saveMeds() se usa para guardar los datos desde la lista local medicamentos hacia la base de datos SQLite.
//    Esta fun se invoca desde la fun onDestroy() del MainActivity y desde la fun addProduct() del AddProductViewModel:
    fun saveMeds(context: Context) {
        *//*        val json = Gson().toJson(medicamentos)
                val json = JSONObject()
                try  {
                    context.openFileOutput("medicamentos.json", Context.MODE_PRIVATE).use {
                        it.write(json.toByteArray())}
                } catch (e: Exception) {
                    e.printStackTrace()
                }
                val medArray = JSONArray()
                for (med: ProductData in medicamentos) {
                    val medicamento = JSONObject()
                    medicamento.put("id", med.id)
                    medicamento.put("nombre", med.nombre)
                    medicamento.put("marca", med.marca)
                    medicamento.put("descripcion", med.descripcion)
                    medicamento.put("precio", med.precio)
                    medicamento.put("categoria", med.categoria)
                    medArray.put(medicamento)
                    }
                json.put("medicamentos", medArray)*//*
        //Para guardar los datos en la base de datos SQLite:
        //Recorremos los medicamentos de la lista local medicamentos:
        for (medicamento: ProductData in medicamentos) {

//           Luego comparamos que el medicamento este o no en la Base de datos SQLite., segun su id
            //El resultado de la comparacion lo guardamos en la val m:
            val m: ProductData? = medDaoHelper.getMedicamentosById(medicamento.id!!)

//           Si el medicamento local no esta en la base de datos SQLite(m= null), lo agregamos, medDaoHelper.addMedicamento(medicamento)
            if (m == null) {
                medDaoHelper.addMedicamento(medicamento)

//            Si el medicamento local ya esta en la base de datos SQLite (else), lo actualizamos, medDaoHelper.updateMedicamento(medicamento)
            } else {
                medDaoHelper.updateMedicamento(medicamento)
            }
        }


    }*/


    }




