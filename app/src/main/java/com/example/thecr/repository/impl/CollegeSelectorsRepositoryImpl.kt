package com.example.thecr.repository.impl

import android.app.Application
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.thecr.model.College
import com.example.thecr.repository.CollegeSelectorsRepository
import com.example.thecr.util.readString
import com.example.thecr.util.writeString
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CollegeSelectorsRepositoryImpl @Inject constructor(
    val firestore: FirebaseFirestore,
    val appContext: Application
) :
    CollegeSelectorsRepository {
    private val COLLEGE_KEY = "college_id"

    override suspend fun getColleges(): List<College> =
        firestore.collection("Colleges").get().await().toObjects(College::class.java)

    override fun collegeID(): Flow<String> = appContext.readString(COLLEGE_KEY)

    override suspend fun saveCollegeID(collegeId: String) {
        appContext.writeString(COLLEGE_KEY,collegeId)
    }
}