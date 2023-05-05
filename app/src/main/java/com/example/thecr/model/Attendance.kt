package com.example.thecr.model

import com.google.firebase.firestore.DocumentId

data class Attendance(
    @DocumentId val teacherId: String = "",
    //epochDays->Day
    val attendance: MutableMap<String, AttendanceDay> = mutableMapOf(),
)

data class AttendanceDay(
    //timetablePeriodId->studentList
    val period: Map<String, StudentList> = mapOf(),
)
