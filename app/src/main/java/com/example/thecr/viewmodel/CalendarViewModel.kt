package com.example.thecr.viewmodel

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.thecr.model.Calendar
import com.example.thecr.model.Course
import com.example.thecr.model.TimetablePeriod
import com.example.thecr.repository.CalendarRepository
import com.example.thecr.util.CalendarHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalDateTime
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class CalendarViewModel @Inject constructor(private val calendarRepository: CalendarRepository):ViewModel() {
    private val _classesList: MutableStateFlow<List<Calendar>> = MutableStateFlow(mutableStateListOf())
    val classesList: StateFlow<List<Calendar>> get() = _classesList

    private val _scheduleList: MutableStateFlow<List<Course>> = MutableStateFlow(mutableStateListOf())
    val scheduleList: StateFlow<List<Course>> get() = _scheduleList

    private val _epochDay: MutableStateFlow<Long> = MutableStateFlow(LocalDate.now().toEpochDay())
    val epochDay: StateFlow<Long> get() = _epochDay

    private val _selectedDay: MutableStateFlow<Int> = MutableStateFlow(LocalDateTime.now().dayOfWeek.value-1)
    val selectedDay: StateFlow<Int> get() = _selectedDay

    private val _timeTablePeriodList: MutableStateFlow<List<TimetablePeriod>> = MutableStateFlow(mutableStateListOf())
    val timeTablePeriodList: StateFlow<List<TimetablePeriod>> get() = _timeTablePeriodList

    fun onSelectedDayChanged(selectedDay:Int){
        _selectedDay.value=selectedDay
    }

    fun onEpochDayChanged(epochDay: Long){
        _epochDay.value=epochDay
    }

    fun getEpochDay(day:Int){
        viewModelScope.launch {
            calendarRepository.getEpochDay(day)
        }
    }

    fun getTimetablePeriod(){
        viewModelScope.launch {
//            calendarRepository.addStudentList()
            _timeTablePeriodList.value= calendarRepository.getTimetablePeriodList(CalendarHelper.dayOfWeek(selectedDay.value))?: mutableListOf()
        }
    }
}