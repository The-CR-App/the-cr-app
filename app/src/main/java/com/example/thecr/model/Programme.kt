package com.example.thecr.model

import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.DocumentReference

data class Programme(
    @DocumentId
    val programmeID:String="",
    val name:String="",
    var years:Map<String,Map<String,List<DocumentReference>>> = mapOf()
)