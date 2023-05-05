package com.example.thecr.model

import com.google.firebase.firestore.DocumentId

data class Student(
    val rollNo: String,
    val name: String,
    val year: Int,
    val branch: String,
)

data class StudentList(
    val studentList: MutableMap<String, Boolean>? = mutableMapOf()
)

data class StudentAttendanceScoreList(
    @DocumentId val year_branch: String="",
    val studentList: MutableMap<String, Double> = mutableMapOf()
)
