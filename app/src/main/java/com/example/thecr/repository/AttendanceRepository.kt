package com.example.thecr.repository

import com.example.thecr.model.StudentList

interface AttendanceRepository {
    suspend fun getAttendanceData(
        day: Int?,
        timetablePeriodId: String,
        year_branch: String
    ): MutableMap<String, Boolean>?

    suspend fun updateAttendanceData(
        day: Int?,
        timetablePeriodId: String,
        year_branch: String,
        studentList: StudentList
    )
}