package com.example.micalendario.network

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive

class HolidayApi(
private val client: HttpClient
) {
    suspend fun getHolidays(year: Int, countryCode: String = "CL"): List<HolidayDto> {
        val url = "https://date.nager.at/api/v3/PublicHolidays/$year/$countryCode"

        val responseText = client.get(url).bodyAsText()

        val jsonArray = Json.parseToJsonElement(responseText).jsonArray

        return jsonArray.map { element ->
            val obj = element.jsonObject
            HolidayDto(
                date = obj["date"]!!.jsonPrimitive.content,
                localName = obj["localName"]!!.jsonPrimitive.content,
                name = obj["name"]!!.jsonPrimitive.content
            )
        }
    }
}
