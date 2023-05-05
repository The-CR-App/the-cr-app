package com.example.thecr.model

import com.google.firebase.firestore.DocumentId
import java.util.TreeMap

data class Course(
    @DocumentId
    val courseId:String="",
    val name:String="",
    val programme:String="",
)