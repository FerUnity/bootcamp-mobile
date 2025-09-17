package com.example.proyectopersonal.services

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

interface MedApiService {
    object RetrofitInstance {
        private const val BASE_URL = "https://ejemplo-firebase-657d0-default-rtdb.firebaseio.com/"

        val api: MedApiService by lazy {
            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(MedApiService::class.java)
        }
    }

    @GET("contacts.json")
    suspend fun getMedicamentos(): List<ProductData>

    @GET("contacts/{id}.json")
    suspend fun getMedicamentoById(@Path("id") id: Int): ProductData?

    @POST("contacts.json")
    suspend fun addMedicamento(@Body contact: ProductData): ProductData

    @PUT("contacts/{id}.json")
    suspend fun updateMedicamento(@Path("id") id: Int, @Body contact: ProductData): ProductData

    @DELETE("contacts/{id}.json")
    suspend fun deleteMedicamento(@Path("id") id: Int)

}