package com.example.thecr.model

import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.DocumentReference

data class Calendar(
    @DocumentId val epochDays: String = "",
    val schedule: Map<String, List<DocumentReference>> = mapOf()
)




