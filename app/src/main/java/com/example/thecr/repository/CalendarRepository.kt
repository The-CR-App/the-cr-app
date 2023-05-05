package com.example.thecr.repository

import com.example.thecr.model.Student
import com.example.thecr.model.TimetablePeriod
import kotlinx.coroutines.flow.MutableStateFlow

interface CalendarRepository {
    val studentList: MutableStateFlow<List<Student>>
    suspend fun getStudentList()
    suspend fun getEpochDay(day:Int)
    suspend fun getTimetablePeriodList(day:String): MutableList<TimetablePeriod>?
    suspend fun addStudentList()
}