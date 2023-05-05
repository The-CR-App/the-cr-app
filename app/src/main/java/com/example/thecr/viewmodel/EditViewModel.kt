package com.example.thecr.viewmodel

import android.util.Log
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.thecr.model.Course
import com.example.thecr.model.Programme
import com.example.thecr.model.Teacher
import com.example.thecr.model.TimeTable
import com.example.thecr.repository.EditRepository
import com.google.firebase.firestore.DocumentReference
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditViewModel @Inject constructor(private val editRepository: EditRepository) : ViewModel() {
    private val _name: MutableStateFlow<String> = MutableStateFlow("")
    val name: StateFlow<String> get() = _name

    private val _timeTable: MutableStateFlow<TimeTable> = MutableStateFlow(TimeTable())
    val timeTable: StateFlow<TimeTable> get() = _timeTable

    private val _scheduleMap: MutableStateFlow<MutableMap<String, List<Int>>> = MutableStateFlow(mutableStateMapOf())
    val scheduleMap: StateFlow<MutableMap<String, List<Int>>> get() = _scheduleMap

    private val _daysList: MutableStateFlow<MutableList<Int>> = MutableStateFlow(mutableStateListOf())
    val daysList: StateFlow<MutableList<Int>> get() = _daysList

    private val _teacher: MutableStateFlow<Teacher> = MutableStateFlow(Teacher())
    val teacher: StateFlow<Teacher> get() = _teacher

    private val _programme: MutableStateFlow<String> = MutableStateFlow("")
    val programme: StateFlow<String> get() = _programme

    private val _programmeList: MutableStateFlow<List<String>> = MutableStateFlow(mutableStateListOf())
    val programmeList: StateFlow<List<String>> get() = _programmeList

    private val _year: MutableStateFlow<String> = MutableStateFlow("")
    val year: StateFlow<String> get() = _year

    private val _branch: MutableStateFlow<String> = MutableStateFlow("")
    val branch: StateFlow<String> get() = _branch

    private val _period: MutableStateFlow<Int> = MutableStateFlow(1)
    val period: StateFlow<Int> get() = _period

    private val _courseReferenceList: MutableStateFlow<List<Course>> = MutableStateFlow(mutableStateListOf())
    val courseReferenceList: StateFlow<List<Course>> get() = _courseReferenceList

    private val _newCourse: MutableStateFlow<Course> = MutableStateFlow(Course())
    val newCourse: StateFlow<Course> get() = _newCourse

    fun onNameChange(name: String) {
        _teacher.update { teacher ->
            Teacher(teacher.id, name)
        }
    }


    fun updateTeacher() {
        editRepository.onTeacherDataChanged(teacher.value)
        viewModelScope.launch {
            editRepository.updateTeacher(teacher.value)
        }
    }

    fun onYearChange(year: String) {
        _year.value = year
    }

    fun onProgrammeChange(programme: String) {
        _programme.value = programme
    }

    fun getProgrammeList() {
        viewModelScope.launch {
            _programmeList.value = editRepository.getProgrammeList().toSet().toList()
        }
    }


    fun fetchTimeTable() {
        viewModelScope.launch {
            _timeTable.value = editRepository.fetchTimeTable()
        }
    }

    fun fetchTeacher() {
        viewModelScope.launch {
            _teacher.value = editRepository.fetchTeacherData()
        }
    }

    fun fetchCourses() {
        viewModelScope.launch {
            _courseReferenceList.value = editRepository.getFilteredCourse(programme.value)
        }
    }

    fun newCourseChanged(course: Course) {
        _newCourse.value = course
    }

    fun onBranchChanged(branch: String) {
        _branch.value = branch
    }

    fun onPeriodChanged(period: Int) {
        _period.value = period
    }

    fun addCourseToTimetable() {
        viewModelScope.launch {
            daysList.value.forEach {
                _timeTable.value = editRepository.addCourseToTimetable(
                    period = period.value,
                    year = year.value.toInt(),
                    branch = branch.value,
                    courseId = newCourse.value.courseId,
                    dayOfWeek = it
                )
            }
        }
    }

    fun fetchCourseList() {
        viewModelScope.launch {
            editRepository.getCourseList()
        }
    }
}