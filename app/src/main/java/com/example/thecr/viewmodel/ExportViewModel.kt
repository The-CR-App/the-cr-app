package com.example.thecr.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.thecr.model.StudentAttendanceScoreList
import com.example.thecr.repository.ExportRepository
import com.example.thecr.util.ExportHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class ExportViewModel @Inject constructor(val exportRepository: ExportRepository) : ViewModel() {
    private val _studentAttendanceScoreList: MutableStateFlow<List<StudentAttendanceScoreList>> = MutableStateFlow(
        mutableListOf()
    )
    val studentAttendanceScoreList: StateFlow<List<StudentAttendanceScoreList>> get() = _studentAttendanceScoreList

    private val _file: MutableStateFlow<File> = MutableStateFlow(
        File("")
    )
    val file: StateFlow<File> get() = _file

    private val _studentAttendanceScore: MutableStateFlow<StudentAttendanceScoreList> = MutableStateFlow(
        StudentAttendanceScoreList()
    )
    val studentAttendanceScore: StateFlow<List<StudentAttendanceScoreList>> get() = _studentAttendanceScoreList


    fun getStudentAttendanceScoreList() {
        viewModelScope.launch {
            _studentAttendanceScoreList.value = exportRepository.getStudentAttendanceScoreList()
        }
    }

    fun exportToCSV(list: StudentAttendanceScoreList,context: Context) {
        viewModelScope.launch {
            _file.value = ExportHelper.exportToCSV(list,context)
        }
    }

    fun exportToPDF(list: StudentAttendanceScoreList) {
        viewModelScope.launch {
            _file.value = ExportHelper.exportToPDF(list)
        }
    }
}