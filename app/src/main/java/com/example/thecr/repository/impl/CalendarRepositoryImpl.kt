package com.example.thecr.repository.impl

import android.app.Application
import android.util.Log
import com.example.thecr.model.*
import com.example.thecr.repository.CalendarRepository
import com.example.thecr.util.readString
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.tasks.await
import kotlinx.datetime.LocalDate
import javax.inject.Inject

class CalendarRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firestore: FirebaseFirestore,
    val appContext: Application
) : CalendarRepository {
    override val studentList: MutableStateFlow<List<Student>>
        get() = MutableStateFlow(mutableListOf())

    override suspend fun getStudentList() {

    }

    override suspend fun getEpochDay(day: Int) {
        val cal = firestore.collection("Colleges").document(appContext.readString("college_id").first())
            .collection("Calendar").document(day.toString()).get().await()
            .let { doc ->
                if (!doc.exists()) doc.reference.set(Calendar())
                doc.reference.get().await().toObject(Calendar::class.java)
            }
    }

    override suspend fun getTimetablePeriodList(day: String) =
        firestore.collection("Colleges").document(appContext.readString("college_id").first())
            .collection("Timetable").document(firebaseAuth.currentUser?.uid!!).get().await()
            .toObject(TimeTable::class.java)?.timetable?.get(day)?.periods

    override suspend fun addStudentList() {
        var map = mutableMapOf<String, Double>()

        for (i in 12012001..12012060) {
            map[i.toString()] = 0.0
        }
        firestore.collection("Colleges").document(appContext.readString("college_id").first())
            .collection("Students").document("3_IT").set(StudentAttendanceScoreList(studentList = map))
    }

}