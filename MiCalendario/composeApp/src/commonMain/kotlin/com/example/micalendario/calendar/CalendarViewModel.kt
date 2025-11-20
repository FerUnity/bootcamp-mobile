package com.example.micalendario.calendar

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.micalendario.calendar.model.DaySelected
import kotlinx.coroutines.launch
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CalendarViewModel @Inject constructor(
    private val datesRepository: DatesRepository
): ViewModel() {
    val datesSelected = datesRepository.datesSelected
    val calendarYear = datesRepository.calendarYear

    fun onDaySelected(daySelected: DaySelected) {
        viewModelScope.launch {
            datesRepository.onDaySelected(daySelected)
        }
    }
}