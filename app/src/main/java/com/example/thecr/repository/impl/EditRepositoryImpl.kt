package com.example.thecr.repository.impl

import android.app.Application
import android.util.Log
import com.example.thecr.model.*
import com.example.thecr.repository.EditRepository
import com.example.thecr.util.CalendarHelper
import com.example.thecr.util.readString
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EditRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firestore: FirebaseFirestore,
    val appContext: Application
) : EditRepository {
    override val currentUserId: String
        get() = firebaseAuth.currentUser?.uid.orEmpty()
    private val _teacher: MutableStateFlow<Teacher> = MutableStateFlow(Teacher())
    override val teacher: Flow<Teacher>
        get() = _teacher

    private val _courseList: MutableStateFlow<List<Course>> = MutableStateFlow(mutableListOf())
    override val courseList: Flow<List<Course>>
        get() = _courseList

    override suspend fun fetchTeacherData() =
        firestore.collection("Colleges").document(appContext.readString("college_id").first())
            .collection("Teachers").document(firebaseAuth.currentUser?.uid!!).get().await()
            .toObject(Teacher::class.java)!!

    override suspend fun getProgrammeList(): List<String> = courseList.first().map { it.programme }
    override suspend fun getFilteredCourse(programme: String): List<Course> =
        courseList.first().filter { course -> course.programme == programme }


    override fun onTeacherDataChanged(teacher: Teacher) {
        _teacher.value = teacher
    }

    override suspend fun getCourses(courseReferenceList: List<DocumentReference>) =
        courseReferenceList.map { it.get().await().toObject(Course::class.java) }

    override suspend fun updateTeacher(teacher: Teacher) {
        firestore.collection("Colleges").document(appContext.readString("college_id").first())
            .collection("Teachers").document(firebaseAuth.currentUser?.uid!!).set(teacher)
    }

    override suspend fun addCourseToTimetable(
        period: Int,
        year: Int,
        branch: String,
        courseId: String,
        dayOfWeek: Int
    ): TimeTable {
        val timeTable = firestore.collection("Colleges").document(appContext.readString("college_id").first())
            .collection("Timetable").document(firebaseAuth.currentUser?.uid!!).get().await().let { doc ->
                if (!doc.exists()) doc.reference.set(TimeTable())
                doc.reference.get().await().toObject(TimeTable::class.java)
            }
        val timetable =
            timeTable?.timetable?.getOrDefault(CalendarHelper.dayOfWeek(dayInt = dayOfWeek), TimetableDay())?.periods?.apply {
                this[period-1] = TimetablePeriod(period = period, year = year, branch = branch, courseId = courseId)
            }

        timeTable?.timetable?.put(CalendarHelper.dayOfWeek(dayInt = dayOfWeek), TimetableDay(periods = timetable!!))
        firestore.collection("Colleges").document(appContext.readString("college_id").first())
            .collection("Timetable").document(firebaseAuth.currentUser?.uid!!).set(timeTable!!)

        return timeTable
    }

    override suspend fun fetchTimeTable(): TimeTable =
        firestore.collection("Colleges").document(appContext.readString("college_id").first())
            .collection("Timetable").document(firebaseAuth.currentUser?.uid!!).get().await().let { doc ->
                if (!doc.exists()) doc.reference.set(TimeTable())
                doc.reference.get().await().toObject(TimeTable::class.java) ?: TimeTable()
            }

    override suspend fun getCourseList() {
        var col = appContext.readString("college_id").first()
        _courseList.value =
            firestore.collection("Colleges").document(appContext.readString("college_id").first()).collection("Course")
                .get().await().toObjects(Course::class.java)

    }
}