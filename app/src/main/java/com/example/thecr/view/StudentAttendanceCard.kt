package com.example.thecr.view

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import com.example.thecr.R
import com.example.thecr.model.Student

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StudentAttendanceCard(studentId: String, status: Boolean, onAttendanceChanged: (String, Boolean) -> Unit) {
    var stat by remember { mutableStateOf(status) }
    Card(
        modifier = Modifier.fillMaxWidth().padding(20.dp),
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White, contentColor = Color.Black),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 5.dp)
//        border = BorderStroke(5.dp, color = Color.Black)
    ) {
        Row(
            modifier = Modifier.padding(20.dp).fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            Column {
                Text(studentId, fontSize = 7.em, fontWeight = FontWeight.Black)
//                Text(student.name)
            }
            Switch(
                checked = stat,
                onCheckedChange = {
                    stat=it
                    onAttendanceChanged(studentId, it)
                },
                colors = SwitchDefaults.colors(
                    checkedThumbColor = Color.White,
                    checkedTrackColor = Color.Black,
                    uncheckedThumbColor = Color.Black,
                    uncheckedTrackColor = Color.White
                )
            )
        }
    }
}
