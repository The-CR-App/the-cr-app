package com.example.thecr.model

import com.google.firebase.firestore.DocumentId

data class Teacher(
    @DocumentId val id: String = "",
    val name: String = "",
)