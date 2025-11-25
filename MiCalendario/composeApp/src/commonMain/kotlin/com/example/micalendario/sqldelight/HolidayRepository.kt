package com.example.micalendario.sqldelight

import kotlinx.datetime.LocalDate
import kotlinx.datetime.toLocalDate

class HolidayRepository(
    private val database: HolidaysDatabase,
    private val api: HolidayApi
) {

    private val queries = database.holidaysQueries

    /**
     * Se llama al iniciar la app para el año actual.
     * Descarga los feriados desde la API y los guarda/actualiza en la BD local.
     */
    suspend fun syncYear(year: Int) {
        try {
            val holidaysFromApi = api.getHolidays(year)
            holidaysFromApi.forEach { dto ->
                queries.insertOrReplace(
                    date = dto.date,
                    localName = dto.localName,
                    name = dto.name
                )
            }
        } catch (e: Exception) {
            println("Error al sincronizar feriados $year: $e")
        }
    }
    /**
     * Devuelve true si la fecha es feriado según la BD local.
     */
    fun isHoliday(date: LocalDate): Boolean {
        val iso = date.toString() // "2025-01-01"
        return queries.selectByDate(iso).executeAsOneOrNull() != null
    }

    /**
     * Lista de todas las fechas feriado del año (desde la BD local).
     */
    fun holidaysByYear(year: Int): Set<LocalDate> {
        return queries.selectByYear("$year-%")
            .executeAsList()
            .map { it.date.toLocalDate() }
            .toSet()
    }
}
