package com.example.thecr.repository.impl

import android.app.Application
import android.util.Log
import com.example.thecr.model.Attendance
import com.example.thecr.model.AttendanceDay
import com.example.thecr.model.StudentAttendanceScoreList
import com.example.thecr.model.StudentList
import com.example.thecr.repository.AttendanceRepository
import com.example.thecr.util.readString
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AttendanceRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firestore: FirebaseFirestore,
    val appContext: Application
) : AttendanceRepository {
    val _attendance: MutableStateFlow<Attendance> = MutableStateFlow(Attendance())
    val attendance: Flow<Attendance>
        get() = _attendance

    override suspend fun getAttendanceData(
        day: Int?,
        timetablePeriodId: String,
        year_branch: String
    ): MutableMap<String, Boolean>? {
        val col = firestore.collection("Colleges").document(appContext.readString("college_id").first())
            .collection("Attendance").document(firebaseAuth.currentUser?.uid!!).get().await()
            .toObject(Attendance::class.java)!!
        return col.attendance.getOrDefault(
            day.toString(),
            AttendanceDay()
        ).period.getOrDefault(
            timetablePeriodId,
            StudentList(firestore.collection("Colleges").document(appContext.readString("college_id").first())
                .collection("Students").document(year_branch).get().await()
                .toObject(StudentAttendanceScoreList::class.java)?.studentList?.mapValues { true }?.toMutableMap()
            )
        )?.studentList
    }


    override suspend fun updateAttendanceData(
        day: Int?,
        timetablePeriodId: String,
        year_branch: String,
        studentList: StudentList
    ) {
        firestore.collection("Colleges").document(appContext.readString("college_id").first())
            .collection("Attendance").document(firebaseAuth.currentUser?.uid!!)
            .update("attendance.$day.period.$timetablePeriodId", studentList)
    }

}