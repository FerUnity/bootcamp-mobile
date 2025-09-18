package com.example.indicadoresmvp.service

import com.example.indicadoresmvp.model.Indicador
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

//Como siempre en ApiService SOLO se encarga de hacer las llamadas a la API con servicios externos Rest y Restful,
// con la URL base que vamos a usar:
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

//    olo requerinos de la API, obtener el valor del indicador por fecha
    @GET("{indicador}/{fecha}")
    suspend fun obtenerIndicadorPorFecha(
        @Path("indicador") indicador: String,
        @Path("fecha") fecha: String
    ): Indicador

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