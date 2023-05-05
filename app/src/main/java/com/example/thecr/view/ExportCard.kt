package com.example.thecr.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.thecr.R
import com.example.thecr.model.StudentAttendanceScoreList
import com.example.thecr.util.CalendarHelper

@Composable
fun ExportCard(
    studentAttendanceScoreList: StudentAttendanceScoreList,
    onExportCSVClick: (StudentAttendanceScoreList) -> Unit,
    onExportPDFClick: (StudentAttendanceScoreList) -> Unit,
) {
    var expanded by remember { mutableStateOf(false) }
    val branch = studentAttendanceScoreList.year_branch.split("_")[1]
    val year = studentAttendanceScoreList.year_branch.split("_")[0]
    Card(
        modifier = Modifier.fillMaxWidth().padding(20.dp),
        shape = RoundedCornerShape(10.dp),
        elevation = CardDefaults.cardElevation(10.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier.padding(20.dp).fillMaxWidth()
                .clickable { expanded = !expanded },
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                "$branch $year${CalendarHelper.getDayNumberSuffix(year.toInt())} year",
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Black
            )
            Image(
                painter = painterResource(R.drawable.pdf_file_icon),
                contentDescription = "Pdf Icon",
                modifier = Modifier.size(30.dp).clickable { onExportPDFClick(studentAttendanceScoreList) },
            )
            Icon(
                painter = painterResource(R.drawable.csv_file_icon),
                contentDescription = "Csv Icon",
                modifier = Modifier.size(30.dp).clickable { onExportCSVClick(studentAttendanceScoreList) }
            )
        }
    }
}
