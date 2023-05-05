package com.example.thecr.view

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.thecr.model.TimetableDay
import com.example.thecr.model.TimetablePeriod
import com.example.thecr.navigation.TheCRScreen
import com.example.thecr.util.CalendarHelper

@Composable
fun TimetableDayCard(timeTableDay: TimetableDay, day: String) {
    var expanded by remember { mutableStateOf(false) }
    var period by remember { mutableStateOf(0) }
    Card(
        modifier = Modifier.fillMaxWidth().padding(20.dp),
        shape = RoundedCornerShape(10.dp),
        elevation = CardDefaults.cardElevation(10.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Text(
            day,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth().background(Color.Black).padding(10.dp),
            color = Color.White,
            fontWeight = FontWeight.Bold
        )
        LazyRow(
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth().padding(20.dp),
        ) {
            items(5) {
                OutlinedButton(
                    onClick = {
                        expanded = true
                        period = it
                    },
                    modifier = Modifier.padding(5.dp),
                    colors = if (period == it) ButtonDefaults.buttonColors(
                        containerColor = Color.Black,
                        contentColor = Color.White
                    ) else ButtonDefaults.buttonColors(containerColor = Color.White, contentColor = Color.Black)
                ) {
                    Text((it + 1).toString())
                }
            }
        }
        AnimatedVisibility(expanded) {
            val timetablePeriod = timeTableDay.periods[period]
            if (timetablePeriod.year == 0) Row(
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth().padding(20.dp)
                    .border(width = 2.dp, color = Color.Black, shape = RoundedCornerShape(20.dp)),
            ) {
                Text(
                    "No Class",
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(10.dp)
                )
            } else
                Row(
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth().padding(20.dp)
                        .border(width = 2.dp, color = Color.Black, shape = RoundedCornerShape(20.dp)),
                ) {
                    Text(
                        "Period\n${timetablePeriod.period}",
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(10.dp)
                    )
                    Text(
                        "Branch\n${timetablePeriod.branch}",
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(10.dp)
                    )
                    Text(
                        "Year\n${timetablePeriod.year}",
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(10.dp)
                    )
                    Text(
                        "Course ID\n${timetablePeriod.courseId}",
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(10.dp)
                    )
                }
        }

    }
}

@Preview(showBackground = true)
@Composable
fun TimetableDayCardPreview() {
    TimetableDayCard(
        timeTableDay = TimetableDay(
            periods = mutableListOf(
                TimetablePeriod("", 0, 1, "IT", "ITL501"),
                TimetablePeriod("", 0, 1, "IT", "ITL501")
            )
        ),
        day = "Monday"
    )
}

@Composable
fun TimetablePeriodCard(timetablePeriod: TimetablePeriod, day: Long?, navHostController: NavController) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(20.dp)
            .clickable { navHostController.navigate("${TheCRScreen.ATTENDANCE_SCREEN.name}/${timetablePeriod.timetablePeriodId}/${day}/${timetablePeriod.year}_${timetablePeriod.branch}") },
        shape = RoundedCornerShape(10.dp),
        elevation = CardDefaults.cardElevation(0.dp),
        border = BorderStroke(1.dp, color = Color.Black),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.heightIn(min = 100.dp, max = 100.dp)
        ) {
            Card(
                modifier = Modifier.padding(20.dp),
                colors = CardDefaults.cardColors(containerColor = Color.Black, contentColor = Color.White)
            ) {
                Text(
                    "${timetablePeriod.period}",
                    modifier = Modifier.padding(20.dp),
                    fontWeight = FontWeight.Black,
                )
            }
            Text(timetablePeriod.courseId, modifier = Modifier.padding(20.dp), fontSize = 25.sp)
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    "${timetablePeriod.year}${CalendarHelper.getDayNumberSuffix(timetablePeriod.year)} year",
                    fontSize = 20.sp
                )
                Text(
                    timetablePeriod.branch,
                    fontSize = 20.sp
                )
            }
        }
    }
}

