package com.example.thecr.model

import com.google.firebase.firestore.DocumentId

data class TimeTable(
    @DocumentId val teacherId: String = "",
    var timetable: MutableMap<String, TimetableDay> = mutableMapOf()
)

data class TimetableDay(
    val periods: MutableList<TimetablePeriod> = MutableList(5) { TimetablePeriod(period = it+1) }
)

data class TimetablePeriod(
    val timetablePeriodId:String=java.util.UUID.randomUUID().toString(),
    val period: Int = 0,
    val year: Int = 0,
    val branch: String = "",
    val courseId: String = ""
)
