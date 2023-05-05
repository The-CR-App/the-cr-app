package com.example.thecr.view.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.PopupProperties
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.thecr.R
import com.example.thecr.navigation.TheCRScreen
import com.example.thecr.viewmodel.CollegeSelectorsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CollegeSelectorScreen(
    modifier: Modifier,
    navHostController: NavHostController,
    viewModel: CollegeSelectorsViewModel = hiltViewModel()
) {

    var expanded by remember { mutableStateOf(false) }
    val collegeID by viewModel.collegeID.collectAsState()
    val colleges by viewModel.colleges.collectAsState()
    val college by viewModel.college.collectAsState()
    LaunchedEffect(Unit) {
        viewModel.getColleges()
    }
    Scaffold {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(it),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(painter = painterResource(R.drawable.college), contentDescription = "College Image")
            OutlinedTextField(
                college.name.ifEmpty { "Choose College" },
                onValueChange = { },
                enabled = false,
                placeholder = { Text("Choose College") },
                shape = RoundedCornerShape(20.dp),
                modifier = modifier.clickable { expanded = true }.padding(20.dp),
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(
                        expanded = expanded
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
                expanded = expanded,
                onDismissRequest = { expanded = false },
                offset = DpOffset((65).dp, (-380).dp)
            ) {
                colleges.forEachIndexed { index, college ->
                    DropdownMenuItem(
                        text = { Text(college.name) },
                        onClick = {
                            expanded = false
                            viewModel.onCollegeValueChanges(college = college)
                        },
                    )
                }
                DropdownMenuItem(
                    text = { Text("Add More") },
                    onClick = {},
                    trailingIcon = { Icon(imageVector = Icons.Default.AddCircle, contentDescription = "Add College") }
                )
            }
            Button(
                {
                    viewModel.saveCollegeID(college.id)
                    navHostController.navigate(TheCRScreen.LOGIN_SCREEN.name)
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color.Black, contentColor = Color.White)
            ) {
                Text("Next")
                Icon(imageVector = Icons.Default.KeyboardArrowRight, contentDescription = "Next")
            }
        }
    }
}