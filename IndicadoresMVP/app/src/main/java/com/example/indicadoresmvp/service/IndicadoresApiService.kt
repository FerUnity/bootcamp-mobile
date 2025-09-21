package com.example.indicadoresmvp.service

import com.example.indicadoresmvp.room.Indicador
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

//Esta Interfaz es parecida al DAO, pero en vez de definir las operaciones de CRUD (Create, Read, Update, Delete)
// que se pueden realizar en la tabla de medicamentos local ROOM, que es lo que hace el DAO,

// el ApiService SOLO se encarga de hacer las llamadas a la API con servicios externos Rest y Restful.
// En este caso se comunica con la API de Firebase con Retrofit/OkHttp.
// O SEA:
// DAO = LOCAL.
// APISERVICE = REMOTO:
interface IndicadoresApiService {
    object ApiInstance {
//        La sgte cte rep la URl comun de todos los servicios de la API:
//        https://www.mindicador.cl/api/{tipo_indicador}
//        Por ej: https://www.mindicador.cl/api/{tipo_indicador}/{dd-mm-yyyy}
//
        private const val BASE_URL = "https://mindicador.cl/api/"
        val api: IndicadoresApiService by lazy {
//            Retrofit es como Room, pero en vez de crerar una instancia de la BD local,
            //  crea una instancia de la API que vamos a usar como Singleton:,
            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(IndicadoresApiService::class.java)
        }
    }

//    Solo requerinos desde la API, obtener el valor del indicador por fecha:

//    Obtener todos los valores de todos un indicador:
//    https://mindicador.cl/api/indicador
    @GET("")
    suspend fun obtenerIndicadores(): List<Indicador>


    //    Obtener el valor de un indicador en especifico: segun la fecha:
    @GET("{indicador}/{fecha}")
    suspend fun obtenerIndicadorPorFecha(
        @Path("indicador") indicador: String,
        @Path("fecha") fecha: String
    ): Indicador

    @POST("indicador")
    fun addIndicador(@Body indicador: Indicador): Indicador

    /*
//    Obtener el valor del indicador en gral
        @GET("{indicador}")
        suspend fun obtenerIndicador(
            @Path("indicador") indicador: String
        ): Indicador


 Obtener el valor del indicador por año:
        @GET("{indicador}/{anno}")
        suspend fun obtenerIndicadorPorAnno(
            @Path("indicador") indicador: String,
            @Path("anno") anno: String
        ): Indicador
    */
}