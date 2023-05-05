package com.example.thecr.repository

import com.example.thecr.model.College
import kotlinx.coroutines.flow.Flow

interface CollegeSelectorsRepository {

    suspend fun getColleges():List<College>

    fun collegeID():Flow<String>

    suspend fun saveCollegeID(collegeId:String)
}
