package com.example.thecr.view.screen

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.example.thecr.R
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.thecr.navigation.TheCRScreen
import com.example.thecr.view.TimetablePeriodCard
import com.example.thecr.viewmodel.CalendarViewModel
import com.himanshoe.kalendar.Kalendar
import com.himanshoe.kalendar.color.KalendarThemeColor
import com.himanshoe.kalendar.component.day.config.KalendarDayColors
import com.himanshoe.kalendar.model.KalendarDay
import com.himanshoe.kalendar.model.KalendarEvent
import com.himanshoe.kalendar.model.KalendarType
import java.time.LocalDate
import java.time.LocalDateTime

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalendarScreen(
    modifier: Modifier,
    navController: NavController,
    viewModel: CalendarViewModel = hiltViewModel()
) {
    val timeTablePeriodList by viewModel.timeTablePeriodList.collectAsState()
    val selectedDay by viewModel.selectedDay.collectAsState()
    val epochDay by viewModel.epochDay.collectAsState()

    LaunchedEffect(timeTablePeriodList, selectedDay) {
        viewModel.getTimetablePeriod()
    }
    LaunchedEffect(Unit) {
        viewModel.onEpochDayChanged(LocalDate.now().toEpochDay())
        viewModel.onSelectedDayChanged(LocalDateTime.now().dayOfWeek.value - 1)
    }
    Scaffold(topBar = {
        TopAppBar(title = { Text("Hi Faculty") }, actions = {
            IconButton(onClick = {
                navController.navigate(TheCRScreen.EXPORT_CSV_SCREEN.name)
            }) {
                Icon(
                    painter = painterResource(R.drawable.export),
                    contentDescription = "Edit Button",
                    modifier = Modifier.size(25.dp)
                )
            }
            IconButton(onClick = {
                navController.navigate(TheCRScreen.EDIT_SCREEN.name)
            }) {
                Icon(Icons.Default.Edit, contentDescription = "Edit Button")
            }
        })
    }) {
        Column(
            modifier = modifier
                .fillMaxSize().padding(it),
        ) {
            Surface(modifier = Modifier.padding(20.dp), color = Color.Transparent) {
                Kalendar(
                    modifier = Modifier.clip(shape = RoundedCornerShape(10)),
                    kalendarType = KalendarType.Firey,
                    kalendarDayColors = KalendarDayColors(textColor = Color.Black, selectedTextColor = Color.White),
                    onCurrentDayClick = { kalendarDay: KalendarDay, kalendarEvents: List<KalendarEvent> ->
                        viewModel.onSelectedDayChanged(kalendarDay.localDate.dayOfWeek.value - 1)
                        viewModel.onEpochDayChanged(kalendarDay.localDate.toEpochDays().toLong())
                    },
                    kalendarThemeColor = KalendarThemeColor(
                        backgroundColor = Color.White,
                        dayBackgroundColor = Color.Black,
                        headerTextColor = Color.Black
                    )
                )
            }
            Text("Classes", modifier = Modifier.padding(20.dp), style = MaterialTheme.typography.headlineMedium)
            LazyColumn {
                items(timeTablePeriodList.filter { it.year != 0 }) {
                    TimetablePeriodCard(it, navHostController = navController, day = epochDay)
                }
            }
        }
    }

}

