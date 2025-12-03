package com.example.micalendariov4.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.micalendariov4.database.Holiday
import com.example.micalendariov4.model.api.HolidayApiService
import com.example.micalendariov4.model.database.CalendarRepository
import com.example.micalendariov4.model.database.HolidayDatabase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.Month

class CalendarViewModel(database: HolidayDatabase) : ViewModel() {
    private val repository = CalendarRepository(database)
    private val apiService = HolidayApiService()
    private val country = "CL"

    private val _holidays = MutableStateFlow<List<Holiday>>(emptyList())
    val holydays: StateFlow<List<Holiday>> = _holidays.asStateFlow()

    init {
        val date = LocalDate.now()
        val year = date.year
        updateHoliday(year)
    }

    fun updateHoliday(year: Int) {
        viewModelScope.launch {
            val holidays = apiService.getHolidays(year, country)
            for (h in holidays) {
                val holidayDate = LocalDate.parse(h.date)
                val holidayYear = holidayDate.year.toLong()
                val holidayMonth = holidayDate.monthValue.toLong()
                val holiDayDay = holidayDate.dayOfMonth.toLong()
                val holidaysExist = repository.getHolidaysFromDate(holidayYear, holidayMonth, holiDayDay)
                if (holidaysExist.isNotEmpty()) {
                    continue
                }
                val holiday = Holiday(
                    id = 0,
                    year = holidayYear,
                    month = holidayMonth,
                    day = holiDayDay,
                    description = h.localName,
                    country = h.countryCode
                )
                repository.insertHoliday(holiday)
            }
        }
    }

    fun getHolidaysFromMonth(month: Month, year: Long) {
        viewModelScope.launch {
            _holidays.value = repository.getHolidaysFromMonth(month, year, country)
        }
    }
}