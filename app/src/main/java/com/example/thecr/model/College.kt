package com.example.thecr.model

import com.google.firebase.firestore.DocumentId

data class College(
    @DocumentId val id: String = "",
    val name:String="",
)