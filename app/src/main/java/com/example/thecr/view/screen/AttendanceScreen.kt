package com.example.thecr.view.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.thecr.model.StudentList
import com.example.thecr.util.CalendarHelper
import com.example.thecr.view.StudentAttendanceCard
import com.example.thecr.viewmodel.AttendanceViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AttendanceScreen(
    modifier: Modifier,
    timetablePeriodId: String?,
    day: Int?,
    year_branch: String,
    navController: NavController,
    viewModel: AttendanceViewModel = hiltViewModel()
) {
    val studentList by viewModel.studentList.collectAsState()
    val onStudentAttendanceChanged = { studentId: String, status: Boolean ->
        viewModel.updateStudentData(studentId, status)
    }
    LaunchedEffect(studentList) {
        viewModel.getAttendanceData(day = day, timetablePeriodId = timetablePeriodId!!, year_branch = year_branch)
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Attendance of ${year_branch.split("_")[1]} - ${year_branch.split("_")[0]} ${
                            CalendarHelper.getDayNumberSuffix(
                                year_branch.split("_")[0].toInt()
                            )
                        } year"
                    )
                },
                navigationIcon = {
                    IconButton({
                        if (navController.previousBackStackEntry != null) navController.popBackStack()
                    }) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }, floatingActionButton = {
            FloatingActionButton(
                {
                    viewModel.updateAttendanceData(
                        day,
                        timetablePeriodId!!,
                        year_branch,
                        StudentList(studentList.toMutableMap())
                    )
                    if (navController.previousBackStackEntry != null) navController.popBackStack()
                },
                containerColor = Color.Black,
                contentColor = Color.White
            ) {
                Icon(imageVector = Icons.Default.Done, contentDescription = "Save Form")
            }
        }) {
        Column(
            modifier = modifier
                .fillMaxSize().padding(it),
        ) {
            LazyColumn {
                items(items = studentList.toSortedMap().toList()) {
                    StudentAttendanceCard(it.first, it.second, onStudentAttendanceChanged)
                }
            }
        }
    }
}

