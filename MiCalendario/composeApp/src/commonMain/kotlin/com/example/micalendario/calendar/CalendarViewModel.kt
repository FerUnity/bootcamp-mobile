package com.example.micalendario.calendar


import com.example.micalendario.sqldelight.HolidayRepository
import kotlinx.coroutines.launch
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.number
import kotlinx.datetime.toLocalDateTime
import kotlin.time.ExperimentalTime

data class CalendarState(
    val currentMonthStart: LocalDate,
    val holidays: Set<LocalDate> = emptySet()
)

class CalendarViewModel(
    private val repo: HolidayRepository
) {

    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Default)

    private val _state: MutableStateFlow<CalendarState>
    val state: StateFlow<CalendarState> get() = _state

    @OptIn(ExperimentalTime::class)
    private val today: LocalDate = kotlin.time.Clock.System.now()
        .toLocalDateTime(TimeZone.currentSystemDefault()).date

    init {
        val firstDayOfMonth = LocalDate(today.year, today.month.number, 1)
        _state = MutableStateFlow(
            CalendarState(currentMonthStart = firstDayOfMonth)
        )

        // Al iniciar, cargamos feriados del año del mes actual
        loadHolidaysForYear(firstDayOfMonth.year)
    }

    fun getToday(): LocalDate = today

    fun goToNextMonth() {
        val current = _state.value.currentMonthStart
        val nextMonth = addMonths(current, 1)
        updateMonth(nextMonth)
    }

    fun goToPreviousMonth() {
        val current = _state.value.currentMonthStart
        val prevMonth = addMonths(current, -1)
        updateMonth(prevMonth)
    }

    fun goToMonthOf(date: LocalDate) {
        val first = LocalDate(date.year, date.month.number, 1)
        updateMonth(first)
    }

    // Centralizamos el cambio de mes y recarga de feriados
    private fun updateMonth(newMonthStart: LocalDate) {
        _state.value = _state.value.copy(currentMonthStart = newMonthStart)
        loadHolidaysForYear(newMonthStart.year)
    }

    private fun loadHolidaysForYear(year: Int) {
        scope.launch {
            repo.syncYear(year)
            val holidaysYear = repo.holidaysByYear(year)
            _state.value = _state.value.copy(
                holidays = _state.value.holidays + holidaysYear
            )
        }
    }


    private fun addMonths(date: LocalDate, delta: Int): LocalDate {
        val newMonthNumber = date.month.number + delta
        val yearShift = floorDiv(newMonthNumber - 1, 12)
        val normalizedMonth = ((newMonthNumber - 1) % 12 + 12) % 12 + 1
        val year = date.year + yearShift
        return LocalDate(year, normalizedMonth, 1)
    }

    private fun floorDiv(a: Int, b: Int): Int {
        val q = a / b
        val r = a % b
        return if (r != 0 && ((a xor b) < 0)) q - 1 else q
    }
}
