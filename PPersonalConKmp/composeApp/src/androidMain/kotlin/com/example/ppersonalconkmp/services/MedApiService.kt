package com.example.ppersonalconkmp.services

import com.example.proyectopersonal.model.ProductData
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import kotlin.getValue

//Esta Interfaz es parecida al DAO, pero en vez de definir las operaciones de CRUD (Create, Read, Update, Delete)
// que se pueden realizar en la tabla de medicamentos local ROOM, que es lo que hace el DAO,

// el ApiService SOLO se encarga de hacer las llamadas a la API con servicios externos Rest y Restful.
// En este caso se comunica con la API de Firebase con Retrofit/OkHttp.
// O SEA:
// DAO = LOCAL.
// APISERVICE = REMOTO:

interface MedApiService {
    object RetrofitInstance {
//        En este caso usaremos como endpoint una API de Firebase, para eso crearemos un archivo json en Firebase y
//        lo consultaremos desde la app para obtener una lista de medicamentos y enlazarla con la BD local ROOM.
        //        pero despues la cambiaremos por el Backend del BootCamp:
        private const val BASE_URL = "https://ejemplo-firebase-657d0-default-rtdb.firebaseio.com/"

        val api: MedApiService by lazy {
            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(MedApiService::class.java)
        }
    }

    @GET("meds_list.json")
    suspend fun getMedicamentos(): List<ProductData>

    @GET("meds_list/{id}.json")
    suspend fun getMedicamentoById(@Path("id") id: Int): ProductData?

    @POST("meds_list.json")
    suspend fun addMedicamento(@Body contact: ProductData): ProductData

    @PUT("meds_list/{id}.json")
    suspend fun updateMedicamento(@Path("id") id: Int, @Body contact: ProductData): ProductData

    @DELETE("meds_list/{id}.json")
    suspend fun deleteMedicamento(@Path("id") id: Int)

}