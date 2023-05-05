package com.example.thecr.view.screen

import android.content.Intent
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.thecr.model.StudentAttendanceScoreList
import com.example.thecr.view.ExportCard
import com.example.thecr.viewmodel.ExportViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExportScreen(
    modifier: Modifier,
    navController: NavController,
    viewModel: ExportViewModel = hiltViewModel()
) {
    val studentAttendanceScoreList by viewModel.studentAttendanceScoreList.collectAsState()
    val file by viewModel.file.collectAsState()
    val context= LocalContext.current
    LaunchedEffect(studentAttendanceScoreList) {
        viewModel.getStudentAttendanceScoreList()
    }
    val launcher= rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()){
        Log.d("say", "ExportScreen: "+it.data?.data?.path)
    }
    val onExportCSVClick = { list: StudentAttendanceScoreList ->
        viewModel.exportToCSV(list,context)
        val openIntent = Intent(Intent.ACTION_VIEW).apply {
            type = "text/csv"
        }
        launcher.launch(Intent.createChooser(openIntent, "Open CSV"))
    }

    val onExportPDFClick = { list: StudentAttendanceScoreList ->
        viewModel.exportToPDF(list)
        val openIntent = Intent(Intent.ACTION_VIEW)
        launcher.launch(Intent.createChooser(openIntent, "Open PDF"))
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Export CSV/PDF") },
                navigationIcon = {
                    IconButton({
                        if (navController.previousBackStackEntry != null) navController.popBackStack()
                    }) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) {
        Column(
            modifier = modifier
                .fillMaxSize().padding(it),
        ) {
            LazyColumn {
                items(studentAttendanceScoreList) {
                    ExportCard(it, onExportCSVClick,onExportPDFClick)
                }
            }
        }

    }
}