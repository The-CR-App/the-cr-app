package com.example.thecr.view.screen

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.thecr.util.CalendarHelper
import com.example.thecr.view.TimetableDayCard
import com.example.thecr.viewmodel.EditViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditScreen(
    modifier: Modifier,
    navController: NavController, viewModel: EditViewModel = hiltViewModel()
) {

    val timeTable by viewModel.timeTable.collectAsState()
    val teacher by viewModel.teacher.collectAsState()
    var openDialog by remember { mutableStateOf(false) }

    var isEditable by remember { mutableStateOf(false) }

    LaunchedEffect(timeTable) {
        viewModel.fetchTeacher()
        viewModel.fetchTimeTable()
        viewModel.fetchCourseList()
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Edit Profile") },
                navigationIcon = {
                    IconButton({
                        if (navController.previousBackStackEntry != null) navController.popBackStack()
                    }) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                {
                    isEditable = !isEditable
                    if (!isEditable)
                        viewModel.updateTeacher()
                },
                containerColor = Color.Black,
                contentColor = Color.White
            ) {
                if (!isEditable)
                    Icon(imageVector = Icons.Default.Edit, contentDescription = "Edit Form")
                else
                    Icon(imageVector = Icons.Default.Done, contentDescription = "Save Form")
            }
        }
    ) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(it)
                .padding(20.dp),
        ) {
            OutlinedTextField(
                teacher.name,
                viewModel::onNameChange,
                enabled = isEditable,
                shape = RoundedCornerShape(20.dp),
                modifier = modifier.fillMaxWidth(),
                label = {
                    Text("Faculty Name")
                },
                placeholder = {
                    Text("Enter Faculty Name")
                },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    disabledTextColor = Color.Black,
                    disabledBorderColor = Color.Black,
                    disabledLabelColor = Color.Black,
                    disabledTrailingIconColor = Color.Black,
                    disabledPlaceholderColor = Color.Black
                )
            )
            Text(text = "Courses", style = MaterialTheme.typography.headlineLarge, modifier = modifier.padding(20.dp))
            LazyColumn {
                items(timeTable.timetable.toList()) {
                    TimetableDayCard(it.second, it.first)
                }
            }
            Surface(
                border = BorderStroke(1.dp, if (isEditable) Color.Black else Color.LightGray),
                shape = RoundedCornerShape(20.dp),
                modifier = modifier
                    .fillMaxWidth()
                    .clickable {
                        if (isEditable)
                            openDialog = true
                    }
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add Courses",
                    modifier = modifier.fillMaxWidth().padding(10.dp),
                    tint = if (isEditable) Color.Black else Color.LightGray
                )
            }
        }

        if (openDialog) {
            var expandedProgramme by remember { mutableStateOf(false) }
            var expandedYear by remember { mutableStateOf(false) }
            var expandedBranch by remember { mutableStateOf(false) }
            var expandedCourse by remember { mutableStateOf(false) }
            var expandedPeriod by remember { mutableStateOf(false) }
            val programmeList by viewModel.programmeList.collectAsState()
            val programme by viewModel.programme.collectAsState()
            val yearList = listOf("1", "2", "3", "4")
            val branchList = listOf("IT", "CSE")
            val periodList = listOf<Int>(1, 2, 3, 4, 5, 6, 7)
            val period by viewModel.period.collectAsState()
            val year by viewModel.year.collectAsState()
            val newCourse by viewModel.newCourse.collectAsState()
            val branch by viewModel.branch.collectAsState()
            val daysList by viewModel.daysList.collectAsState()

            LaunchedEffect(programmeList) {
                viewModel.getProgrammeList()
            }
            AlertDialog(
                {
                    openDialog = false
                },
                modifier = with(Modifier) {
                    fillMaxWidth().clip(RoundedCornerShape(20.dp)).background(Color.White)
                }) {
                Column(
                    verticalArrangement = Arrangement.SpaceEvenly,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = modifier.padding(20.dp).verticalScroll(rememberScrollState())
                ) {
                    Text("Add Course")
                    ExposedDropdownMenuBox(expandedProgramme, onExpandedChange = {
                        expandedProgramme = !expandedProgramme
                    }, modifier = modifier.padding(20.dp)) {
                        OutlinedTextField(
                            programme.ifEmpty { "Choose Programme" },
                            onValueChange = { },
                            enabled = false,
                            placeholder = { Text("Choose Programme") },
                            shape = RoundedCornerShape(20.dp),
                            modifier = modifier.clickable { expandedProgramme = true }.menuAnchor(),
                            trailingIcon = {
                                ExposedDropdownMenuDefaults.TrailingIcon(
                                    expanded = expandedProgramme
                                )
                            },
                            colors = TextFieldDefaults.outlinedTextFieldColors(
                                disabledBorderColor = Color.Black,
                                disabledTextColor = Color.Black,
                                disabledTrailingIconColor = Color.Black,
                                disabledLabelColor = Color.Black
                            )
                        )
                        DropdownMenu(
                            expanded = expandedProgramme,
                            onDismissRequest = { expandedProgramme = false },
                        ) {
                            programmeList.forEachIndexed { index, programme1 ->
                                DropdownMenuItem(
                                    text = { Text(programme1) },
                                    onClick = {
                                        expandedProgramme = false
                                        viewModel.onProgrammeChange(programme = programme1)
                                    },
                                )
                            }
                        }
                    }
                    ExposedDropdownMenuBox(expandedYear, onExpandedChange = {
                        expandedYear = !expandedYear
                    }, modifier = modifier.padding(20.dp)) {
                        OutlinedTextField(
                            year.ifEmpty { "Choose Year" },
                            onValueChange = { },
                            enabled = false,
                            placeholder = { Text("Choose Year") },
                            shape = RoundedCornerShape(20.dp),
                            modifier = modifier.clickable { expandedYear = true }.menuAnchor(),
                            trailingIcon = {
                                ExposedDropdownMenuDefaults.TrailingIcon(
                                    expanded = expandedYear
                                )
                            },
                            colors = TextFieldDefaults.outlinedTextFieldColors(
                                disabledBorderColor = Color.Black,
                                disabledTextColor = Color.Black,
                                disabledTrailingIconColor = Color.Black,
                                disabledLabelColor = Color.Black
                            )
                        )
                        DropdownMenu(
                            expanded = expandedYear,
                            onDismissRequest = { expandedYear = false },
                        ) {
                            yearList.forEach { year ->
                                DropdownMenuItem(
                                    text = { Text(year) },
                                    onClick = {
                                        expandedYear = false
                                        viewModel.onYearChange(year)
                                    },
                                )
                            }
                        }
                    }
                    ExposedDropdownMenuBox(expandedBranch, onExpandedChange = {
                        expandedBranch = !expandedBranch
                    }, modifier = modifier.padding(20.dp)) {
                        OutlinedTextField(
                            branch.ifEmpty { "Choose Branch" },
                            onValueChange = { },
                            enabled = false,
                            placeholder = { Text("Choose Branch") },
                            shape = RoundedCornerShape(20.dp),
                            modifier = modifier.clickable { expandedBranch = true }.menuAnchor(),
                            trailingIcon = {
                                ExposedDropdownMenuDefaults.TrailingIcon(
                                    expanded = expandedBranch
                                )
                            },
                            colors = TextFieldDefaults.outlinedTextFieldColors(
                                disabledBorderColor = Color.Black,
                                disabledTextColor = Color.Black,
                                disabledTrailingIconColor = Color.Black,
                                disabledLabelColor = Color.Black
                            )
                        )
                        DropdownMenu(
                            expanded = expandedBranch,
                            onDismissRequest = { expandedBranch = false },
                        ) {
                            branchList.forEach { branch ->
                                DropdownMenuItem(
                                    text = { Text(branch) },
                                    onClick = {
                                        expandedBranch = false
                                        viewModel.onBranchChanged(branch)
                                        viewModel.fetchCourses()
                                    },
                                )
                            }
                        }
                    }
                    ExposedDropdownMenuBox(expandedCourse, onExpandedChange = {
                        expandedCourse = !expandedCourse
                    }, modifier = modifier.padding(20.dp)) {
                        OutlinedTextField(
                            newCourse.name.ifEmpty { "Choose Course" },
                            onValueChange = { },
                            enabled = false,
                            placeholder = { Text("Choose Course") },
                            shape = RoundedCornerShape(20.dp),
                            modifier = modifier.clickable { expandedCourse = true }.menuAnchor(),
                            trailingIcon = {
                                ExposedDropdownMenuDefaults.TrailingIcon(
                                    expanded = expandedCourse
                                )
                            },
                            colors = TextFieldDefaults.outlinedTextFieldColors(
                                disabledBorderColor = Color.Black,
                                disabledTextColor = Color.Black,
                                disabledTrailingIconColor = Color.Black,
                                disabledLabelColor = Color.Black
                            )
                        )
                        DropdownMenu(
                            expanded = expandedCourse,
                            onDismissRequest = { expandedCourse = false },
                        ) {
                            val courseReferenceList by viewModel.courseReferenceList.collectAsState()
                            courseReferenceList.forEach { course ->
                                DropdownMenuItem(
                                    text = { Text(course.name) },
                                    onClick = {
                                        expandedCourse = false
                                        viewModel.newCourseChanged(course)
                                    },
                                )
                            }
                        }
                    }
                    ExposedDropdownMenuBox(expandedPeriod, onExpandedChange = {
                        expandedPeriod = !expandedPeriod
                    }, modifier = modifier.padding(20.dp)) {
                        OutlinedTextField(
                            period.toString().ifEmpty { "Choose Period" },
                            onValueChange = { },
                            enabled = false,
                            placeholder = { Text("Choose Period") },
                            shape = RoundedCornerShape(20.dp),
                            modifier = modifier.clickable { expandedPeriod = true }.menuAnchor(),
                            trailingIcon = {
                                ExposedDropdownMenuDefaults.TrailingIcon(
                                    expanded = expandedPeriod
                                )
                            },
                            colors = TextFieldDefaults.outlinedTextFieldColors(
                                disabledBorderColor = Color.Black,
                                disabledTextColor = Color.Black,
                                disabledTrailingIconColor = Color.Black,
                                disabledLabelColor = Color.Black
                            )
                        )
                        DropdownMenu(
                            expanded = expandedPeriod,
                            onDismissRequest = { expandedPeriod = false },
                        ) {
                            periodList.forEach { period ->
                                DropdownMenuItem(
                                    text = { Text(period.toString()) },
                                    onClick = {
                                        expandedCourse = false
                                        viewModel.onPeriodChanged(period)
                                    },
                                )
                            }
                        }
                    }
                    LazyRow {
                        items(5) {
                            AssistChip(
                                onClick = {
                                    if (daysList.contains(it)) daysList.remove(element = it) else daysList.add(
                                        it
                                    )
                                },
                                label = { Text(CalendarHelper.dayOfWeek(it).substring(0..1)) },
                                modifier = modifier.padding(10.dp),
                                colors = if (daysList.contains(it)) AssistChipDefaults.assistChipColors(
                                    containerColor = Color.Black,
                                    labelColor = Color.White,
                                ) else AssistChipDefaults.assistChipColors()
                            )
                        }
                    }

                    Spacer(modifier = modifier.height(20.dp))
                    Row {
                        Button({ openDialog = false }) {
                            Text("Cancel")
                        }
                        Spacer(modifier = Modifier.width(20.dp))
                        Button({
                            viewModel.addCourseToTimetable()
                            openDialog = false
                        }) {
                            Text("Add")
                        }
                    }
                }
            }
        }
    }
}

