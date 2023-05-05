package com.example.thecr.repository.impl

import android.app.Application
import com.example.thecr.model.StudentAttendanceScoreList
import com.example.thecr.repository.EditRepository
import com.example.thecr.repository.ExportRepository
import com.example.thecr.util.readString
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ExportRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firestore: FirebaseFirestore,
    val appContext: Application
) : ExportRepository {
    override suspend fun getStudentAttendanceScoreList(): List<StudentAttendanceScoreList> =
        firestore.collection("Colleges").document(appContext.readString("college_id").first())
            .collection("Students").get().await().toObjects(StudentAttendanceScoreList::class.java)

}
