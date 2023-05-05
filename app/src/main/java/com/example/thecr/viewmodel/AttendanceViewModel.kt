package com.example.thecr.viewmodel

import androidx.compose.runtime.mutableStateMapOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.thecr.model.StudentList
import com.example.thecr.repository.AttendanceRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AttendanceViewModel @Inject constructor(val attendanceRepository: AttendanceRepository) : ViewModel() {
    private val _studentList: MutableStateFlow<MutableMap<String, Boolean>> = MutableStateFlow(mutableStateMapOf())
    val studentList: StateFlow<Map<String, Boolean>> get() = _studentList

    fun getAttendanceData(day: Int?, timetablePeriodId: String, year_branch: String) {
        viewModelScope.launch {
            _studentList.value = attendanceRepository.getAttendanceData(day, timetablePeriodId,year_branch)!!.toMutableMap()
        }
    }

    fun updateStudentData(studentId: String, status: Boolean) {
        viewModelScope.launch {
            _studentList.update {
                it.apply {
                    set(studentId, status)
                }
            }
        }
    }

    fun updateAttendanceData(day: Int?,timetablePeriodId: String,year_branch: String,studentList: StudentList){
        viewModelScope.launch {
            attendanceRepository.updateAttendanceData(day, timetablePeriodId, year_branch, studentList)
        }
    }
}