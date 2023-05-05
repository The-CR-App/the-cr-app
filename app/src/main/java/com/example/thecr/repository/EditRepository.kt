package com.example.thecr.repository

import com.example.thecr.model.Course
import com.example.thecr.model.Teacher
import com.example.thecr.model.TimeTable
import com.google.firebase.firestore.DocumentReference
import kotlinx.coroutines.flow.Flow

interface EditRepository {
    val currentUserId: String
    val teacher: Flow<Teacher>
    val courseList: Flow<List<Course>>

    suspend fun fetchTeacherData(): Teacher

    suspend fun getProgrammeList(): List<String>
    suspend fun getFilteredCourse(programme: String): List<Course>
    fun onTeacherDataChanged(teacher: Teacher)
    suspend fun getCourses(courseReferenceList: List<DocumentReference>): List<Course?>
    suspend fun updateTeacher(teacher: Teacher)
    suspend fun getCourseList()
    suspend fun addCourseToTimetable(
        period: Int,
        year: Int,
        branch: String,
        courseId: String,
        dayOfWeek: Int,
    ): TimeTable

    suspend fun fetchTimeTable(): TimeTable
}