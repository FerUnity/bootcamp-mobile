package com.example.micalendariov4.model.api

import io.ktor.client.call.body
import io.ktor.client.request.get

class HolidayApiService {
    private val client = HttpClientFactory.client
    private val baseUrl = "https://date.nager.at/api/v3/PublicHolidays"

    suspend fun getHolidays(year: Int, country: String): List<Holiday> {
        return client.get("$baseUrl/$year/$country").body()
    }
}