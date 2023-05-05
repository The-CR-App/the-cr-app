package com.example.thecr.repository

import com.example.thecr.model.StudentAttendanceScoreList

interface ExportRepository {
    suspend fun getStudentAttendanceScoreList():List<StudentAttendanceScoreList>
}